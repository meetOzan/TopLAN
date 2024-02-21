package com.gdscedirne.toplan.presentation.policys

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.common.Constants
import com.gdscedirne.toplan.components.CustomText

@Composable
fun PrivacyPolicy(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        CustomText(text = Constants.PRIVACY_POLICY)
    }
}