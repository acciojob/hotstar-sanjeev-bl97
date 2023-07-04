package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        User user1 = userRepository.save(user);
        return user1.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository

        List<WebSeries> webSeries = webSeriesRepository.findAll();
        Integer count = 0;
        User user = userRepository.findById(userId).get();
        SubscriptionType subscriptionType = user.getSubscription().getSubscriptionType();

        for(WebSeries webSeries1: webSeries){

            if(user.getAge() >= webSeries1.getAgeLimit()){

                if(subscriptionType.equals(SubscriptionType.ELITE))
                    count++;
                else if(subscriptionType.equals(SubscriptionType.PRO)){
                    if(!webSeries1.getSubscriptionType().equals(SubscriptionType.ELITE))
                        count++;
                }
                else{
                    if(!webSeries1.getSubscriptionType().equals(SubscriptionType.ELITE) && !webSeries1.getSubscriptionType().equals(SubscriptionType.PRO))
                        count++;
                }


            }
        }

        return count;
    }


}
