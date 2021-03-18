package com.meeting_test;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.list.ListMeetingActivity;
import com.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.utils.RecyclerViewItemCountAssertion.notWithItemCount;
import static com.utils.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingsListTest {

    private int ITEMS_COUNT;

    @Rule
    public ActivityTestRule mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        MeetingApiService mApiService = DI.getMeetingApiService();
        ITEMS_COUNT = mApiService.getMeetings().size();
    }

    @Test
    public void myMeetingList_roomFilter_shouldShowSelectedRoom() {
        onView(withContentDescription("Filter")).perform(click());
        onView(withText("Salle")).perform(click());
        onView(withText("Salle A")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(notWithItemCount(ITEMS_COUNT));
    }

    @Test
    public void myMeetingList_dateFilter_shouldShowSelectedDate() {
        onView(withContentDescription("Filter")).perform(click());
        onView(withText("Date")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(notWithItemCount(ITEMS_COUNT));
    }

    @Test
    public void myMeetingList_reset_shouldShowAllMeetings() {
        onView(withContentDescription("Filter")).perform(click());
        onView(withText("Salle")).perform(click());
        onView(withText("Salle A")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(notWithItemCount(ITEMS_COUNT));
        onView(withContentDescription("Filter")).perform(click());
        onView(withText("Reset")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void myMeetingList_addAction_shouldAddItem() {
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.subjectEdit)).perform(typeText("TEST"));
        closeSoftKeyboard();
        onView(withId(R.id.participantsEdit)).perform(typeText("TEST@TEST.TEST"));
        closeSoftKeyboard();
        onView(withId(R.id.addParticipantButton)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(ITEMS_COUNT + 1));
    }

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.meetingRecyclerView))
                .perform(actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(ITEMS_COUNT - 1));
    }
}
