package com.yelai.wearable.ui.course

import com.yelai.wearable.model.CourseTime

/**
 * Created by hr on 18/9/16.
 */

class WeekCourseFragment : TodayCourseFragment() {

    override fun onRefresh() {
        p.weekCourseTimesList()
    }

    companion object {

        fun newInstance(): WeekCourseFragment {
            val fragment = WeekCourseFragment()
            return fragment
        }
    }
}
