package cn.itcast.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈友增
 */
@Data
public abstract class Message implements Serializable {

    /**
     * 根据消息类型字节，获取响应的消息class
     *
     * @param messageType 消息类型字节
     * @return 消息class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;
    private int messageType;

    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int PingMessage = 14;
    public static final int PongMessage = 15;

    /**
     * 请求类型byte值
     */
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    /**
     * 响应类型byte值
     */
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
    }
}
