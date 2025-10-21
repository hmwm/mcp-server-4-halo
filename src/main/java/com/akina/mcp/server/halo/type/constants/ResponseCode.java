package com.akina.mcp.server.halo.type.constants;

public enum ResponseCode {
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    HALO_API_ERROR(1001, "Halo API 调用失败"),
    HALO_API_NULL_RESPONSE(1002, "Halo API 返回为空");
    ;

    private final Integer code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
