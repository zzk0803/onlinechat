package zzk.webproject.air;

public enum MessageType {
    /**
     * 短消息，这种类型websocket可以直接推送
     */
    SHORT_MESSAGE,

    /**
     * 长文本，图片等多媒体类型，这种类型通过servlet提交，websocket推送其URL，
     * 前端通过AJAX拉取
     */
    REFERENCE,

    /**
     * 系统信息，推送用户上下线
     */
    SYSTEM_MESSAGE,

    /**
     * 用户管理类信息，如禁言或取消禁言
     */
    ACCOUNT_ADMINISTER,
}
