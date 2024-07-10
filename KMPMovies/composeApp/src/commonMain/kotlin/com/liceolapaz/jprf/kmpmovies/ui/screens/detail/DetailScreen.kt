package com.liceolapaz.jprf.kmpmovies.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.liceolapaz.jprf.kmpmovies.data.Movie
import com.liceolapaz.jprf.kmpmovies.ui.common.LoadingIndicator
import com.liceolapaz.jprf.kmpmovies.ui.screens.Screen
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.back
import kmpmovies.composeapp.generated.resources.favorite
import kmpmovies.composeapp.generated.resources.original_language
import kmpmovies.composeapp.generated.resources.original_title
import kmpmovies.composeapp.generated.resources.popularity
import kmpmovies.composeapp.generated.resources.release_date
import kmpmovies.composeapp.generated.resources.vote_average
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {
    val state = vm.state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen {
        Scaffold(
            topBar = {
                DetailTopBar(
                    title = state.movie?.title ?: "",
                    onBack = onBack,
                    scrollBehavior = scrollBehavior)
            },
            floatingActionButton = {
                state.movie?.let { movie ->
                    FloatingActionButton(
                        onClick = vm::onFavoriteClick
                    ) {
                        Icon(
                            imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(Res.string.favorite)
                        )
                    }
                }
            }
        ) { padding ->
            LoadingIndicator(
                isEnabled = state.isLoading,
                modifier = Modifier.padding(padding)
            )
            state.movie?.let { movie ->
                MovieDetail(
                    movie = movie,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdrop ?: movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Text(
            text = movie.overview,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = buildAnnotatedString {
                property(stringResource(Res.string.original_title), movie.originalTitle)
                property(stringResource(Res.string.original_language), movie.originalLanguage)
                property(stringResource(Res.string.release_date), movie.releaseDate)
                property(stringResource(Res.string.popularity), movie.popularity.toString())
                property(stringResource(Res.string.vote_average), movie.voteAverage.toString(), addNewLine = false)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

private fun AnnotatedString.Builder.property(name: String, value: String, addNewLine: Boolean = true) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (addNewLine) {
            append("\n")
        }
    }
}