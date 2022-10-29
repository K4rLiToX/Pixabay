package com.carlosdiestro.pixabay.images.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.carlosdiestro.pixabay.R
import com.carlosdiestro.pixabay.images.ui.models.SimpleImagePLO
import com.carlosdiestro.pixabay.utils.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ImagesScreen(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val keyboardManager = LocalSoftwareKeyboardController.current

    val openDialog = remember {
        mutableStateOf(false)
    }

    val itemId = remember {
        mutableStateOf(-1)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val (searchInput, warning, imageList) = createRefs()

        val query = rememberSaveable {
            mutableStateOf(Constants.DEFAULT_QUERY)
        }
        OutlinedTextField(
            modifier = Modifier
                .constrainAs(searchInput) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            shape = CircleShape,
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { viewModel.onEvent(ImagesEvent.SubmitQuery(query.value)) }
                        .size(24.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Button"
                )
            },
            value = query.value,
            onValueChange = {
                query.value = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.onEvent(ImagesEvent.SubmitQuery(query.value))
                    keyboardManager?.hide()
                }
            )
        )

        if (state.value.images.isEmpty()) {
            Column(
                modifier = Modifier
                    .constrainAs(warning) {
                        start.linkTo(parent.start)
                        top.linkTo(searchInput.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    tint = Color.Companion.LightGray,
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info Icon"
                )
                Text(
                    text = stringResource(id = R.string.title_warning),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .constrainAs(imageList) {
                        start.linkTo(parent.start)
                        top.linkTo(searchInput.bottom, margin = 24.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(
                    items = state.value.images,
                    key = { img -> img.id }
                ) { img ->
                    ImageCard(
                        image = img,
                        onClick = {
                            openDialog.value = true
                            itemId.value = it
                        }
                    )
                }
            }
        }
    }

    if (openDialog.value && itemId.value != -1) {
        ConfirmationDialog(
            onConfirmation = {
                navController.navigate("image_detail/${itemId.value}")
            },
            onDismiss = {
                openDialog.value = false
                itemId.value = -1
            }
        )
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: SimpleImagePLO,
    onClick: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(image.id) }
    ) {
        val (thumbnail, userName, tags) = createRefs()

        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(20F))
                .constrainAs(thumbnail) {
                    start.linkTo(parent.start)
                    top.linkTo(userName.top)
                    bottom.linkTo(tags.bottom)
                },
            contentScale = ContentScale.FillBounds,
            model = image.thumbnail,
            contentDescription = "Image thumbnail"
        )

        Text(
            modifier = Modifier
                .constrainAs(userName) {
                    start.linkTo(thumbnail.end, margin = 12.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            text = image.userName,
            fontSize = 24.sp
        )

        Text(
            modifier = Modifier
                .constrainAs(tags) {
                    start.linkTo(userName.start)
                    top.linkTo(userName.bottom)
                },
            text = image.tags,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirmation: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirmation
            ) {
                Text(
                    text = stringResource(id = R.string.action_go_details)
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.action_close)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.title_dialog))
        }
    )
}