package com.rafiul.tabcompose.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rafiul.tabcompose.model.TabItem
import com.rafiul.tabcompose.model.tabItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {

        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }

    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.Green,
                    height = 2.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            divider = {
                Divider(thickness = 0.dp, color = MaterialTheme.colorScheme.background)
            }
        ) {
            tabItems.forEachIndexed { index: Int, tabItem: TabItem ->

                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = tabItem.title, style = MaterialTheme.typography.bodySmall)
                    },
                    icon = {
                        Icon(
                            imageVector = tabItem.takeIf { index == selectedTabIndex }?.selectedIcon
                                ?: tabItem.unSelectedIcon,
                            contentDescription = tabItem.title
                        )
                    },
                    selectedContentColor = Color.Green,
                    unselectedContentColor = Color.White,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = tabItems[page].title, style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }

        }

    }

}

