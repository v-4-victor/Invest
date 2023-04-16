package com.v4victor.chart

import com.v4victor.core.dto.CompanyProfile

fun icon(companyProfile: CompanyProfile): Int =
    if (companyProfile.favourite) R.drawable.star_list_focus else R.drawable.star_list_no_focus