package com.carlosdiestro.pixabay.image_details.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    navController: NavController,
    viewModel: ImageDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState, true)
    ) {
        val (topBar, image, userName, tags, social) = createRefs()

        state.value.image?.let { img ->
            TopAppBar(
                modifier = Modifier
                    .constrainAs(topBar) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                title = {},
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { navController.popBackStack() },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Arrow"
                    )
                }
            )

            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(20F))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(topBar.bottom, margin = 8.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                model = img.imageUrl,
                contentDescription = "Image"
            )

            Text(
                modifier = Modifier.constrainAs(userName) {
                    start.linkTo(parent.start)
                    top.linkTo(image.bottom, margin = 12.dp)
                },
                text = img.userName,
                fontSize = 24.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(tags) {
                        start.linkTo(parent.start)
                        top.linkTo(userName.bottom, margin = 4.dp)
                    },
                text = img.tags,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .constrainAs(social) {
                        start.linkTo(parent.start)
                        top.linkTo(tags.bottom, margin = 16.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialCard(icon = Icons.Rounded.ThumbUp, number = img.likes)
                SocialCard(icon = Icons.Rounded.Share, number = img.downloads)
                SocialCard(icon = Icons.Rounded.Place, number = img.comments)
            }
        }
    }
}

@Composable
fun SocialCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    number: Int
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {

        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            imageVector = icon,
            tint = Color.LightGray,
            contentDescription = "Icon"
        )

        Text(
            text = "$number",
            fontSize = 14.sp
        )
    }
}