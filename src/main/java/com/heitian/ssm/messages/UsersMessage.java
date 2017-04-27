/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.heitian.ssm.messages;

import java.util.List;

/* Represents the list of users currently connected to the chat */
public class UsersMessage extends Message {
    private List<String> userlist;
    
    public UsersMessage(List<String> userlist) {
        this.userlist = userlist;
    }
    
    public List<String> getUserList() {
        return userlist;
    }
    
    /* For logging purposes */
    @Override
    public String toString() {
        return "[UsersMessage] " + userlist.toString();
    }
}
