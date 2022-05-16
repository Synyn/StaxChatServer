package com.staxchat;

import com.staxchat.constants.Environment;
import com.staxchat.core.Server;

public class Main {
    public static void main(String[] args) {
        Server.start(Environment.LOCAL);
    }
}
