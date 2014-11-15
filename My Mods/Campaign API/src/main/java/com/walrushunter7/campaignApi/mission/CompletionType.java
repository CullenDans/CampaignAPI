package com.walrushunter7.campaignApi.mission;

public enum CompletionType {

    TEST, KILL_ENTITY, KILL_ENTITIES, KILL_MONSTERS;

    private CompletionType() {

    }

    @Deprecated
    public static CompletionType fromString(String s) {
        return CompletionType.valueOf(s);
    }
}
