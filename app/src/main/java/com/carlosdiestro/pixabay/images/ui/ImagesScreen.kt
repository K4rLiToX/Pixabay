package com.carlosdiestro.pixabay.images.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.carlosdiestro.pixabay.images.ui.models.SimpleImagePLO
import com.carlosdiestro.pixabay.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesScreen(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val (searchInput, searchButton, imageList) = createRefs()

        val query = rememberSaveable {
            mutableStateOf(Constants.DEFAULT_QUERY)
        }
        OutlinedTextField(
            modifier = Modifier
                .constrainAs(searchInput) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(searchButton.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            value = query.value,
            onValueChange = {
                query.value = it
            }
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(searchButton) {
                    top.linkTo(searchInput.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(searchInput.bottom)
                },
            onClick = { viewModel.onEvent(ImagesEvent.SubmitQuery(query.value)) }
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Button"
            )
        }

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = state.value.images,
                key = { img -> img.id }
            ) { img ->
                ImageCard(
                    image = img,
                    onClick = {
                        "${navController.navigate(" image_detail")}/$it"
                    }
                )
            }
        }
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
                .clip(CircleShape)
                .constrainAs(thumbnail) {
                    start.linkTo(parent.start)
                    top.linkTo(userName.top)
                    bottom.linkTo(tags.bottom)
                },
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
                    top.linkTo(userName.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom)
                },
            text = image.tags,
            fontSize = 16.sp
        )
    }
}