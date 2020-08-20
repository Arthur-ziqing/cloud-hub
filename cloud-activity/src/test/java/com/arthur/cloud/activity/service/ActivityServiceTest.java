//package com.arthur.cloud.activity.service;
//
//import com.arthur.cloud.activity.model.User;
//import com.arthur.cloud.activity.model.condition.ActivityCondition;
//import com.arthur.cloud.activity.model.condition.UserActivityCondition;
//import com.arthur.cloud.activity.model.enums.ActivityEnums;
//import com.arthur.cloud.activity.model.enums.UserActivityEnum;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("dev")
//public class ActivityServiceTest {
//
//    @Autowired
//    private ActivityService activityService;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    @Transactional
//    public void openPrize() {
//        activityService.openPrize(5L,"o1RZA5fL-P43iv00MBAzhowifVaM");
//    }
//
//    @Test
//    public void queryPageByType() {
//        UserActivityCondition condition  = new UserActivityCondition();
//        condition.setType(UserActivityEnum.ALL);
//        activityService.queryPageByType(condition,"o1RZA5fL-P43iv00MBAzhowifVaM");
//    }
//
//    @Test
//    public void in() {
//        ActivityCondition activityCondition = new ActivityCondition();
//        activityCondition.setActivityType(ActivityEnums.FINISH);
//        activityService.queryJoin(activityCondition,"o1RZA5fL-P43iv00MBAzhowifVaM");
//    }
//}
