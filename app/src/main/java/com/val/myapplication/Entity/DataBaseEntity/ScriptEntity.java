package com.val.myapplication.Entity.DataBaseEntity;

public class ScriptEntity {
    private String scriptName;
    private String scriptContent;

    public ScriptEntity() {
    }

    public ScriptEntity(String scriptName, String scriptContent) {
        this.scriptName = scriptName;
        this.scriptContent = scriptContent;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }
}
