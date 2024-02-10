package com.github.fadegor05.cli;

import com.github.fadegor05.models.Instance;

public class InstanceOutCli {
    public static void InstanceOutCli(Instance instance) {
        System.out.printf("· %s%n", instance.getName());
        System.out.printf("‣ Версия %s%n", instance.getVersion());
        System.out.printf("‣ %s%n%n",instance.getChangelog());

    }
}
