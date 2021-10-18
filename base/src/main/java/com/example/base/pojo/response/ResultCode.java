package com.example.base.pojo.response;

import org.springframework.http.HttpStatus;

public enum ResultCode {

    //成功
    SUCCESS(0),

    //失败
    ERROR(-1),

    CONTINUE(100),

    //{@code 101 Switching Protocols}
    SWITCHING_PROTOCOLS(101),

    //{@code 102 Processing}
    PROCESSING(102),

    //{@code 103 Checkpoint}
    CHECKPOINT(103),

    // 2xx Success

    //{@code 200 OK}
    OK(200),

    //{@code 201 Created}
    CREATED(201),

    //{@code 202 Accepted}
    ACCEPTED(202),

    //{@code 203 Non-Authoritative Information}
    NON_AUTHORITATIVE_INFORMATION(203),

    //{@code 204 No Content}
    NO_CONTENT(204),

    //{@code 205 Reset Content}
    RESET_CONTENT(205),

    //{@code 206 Partial Content}
    PARTIAL_CONTENT(206),

    //{@code 207 Multi-Status}
    MULTI_STATUS(207),

    //{@code 208 Already Reported}
    ALREADY_REPORTED(208),

    //{@code 226 IM Used}
    IM_USED(226),

    // 3xx Redirection


    //{@code 300 Multiple Choices}
    MULTIPLE_CHOICES(300),

    //{@code 301 Moved Permanently}
    MOVED_PERMANENTLY(301),

    //{@code 302 Found}
    FOUND(302),

    //{@code 303 See Other}
    SEE_OTHER(303),

    //{@code 304 Not Modified}
    NOT_MODIFIED(304),

    //{@code 307 Temporary Redirect}
    TEMPORARY_REDIRECT(307),

    //{@code 308 Permanent Redirect}
    PERMANENT_REDIRECT(308),

    // --- 4xx Client Error ---


    //{@code 400 Bad Request}
    BAD_REQUEST(400),

    //{@code 401 Unauthorized}
    UNAUTHORIZED(401),

    //{@code 402 Payment Required}
    PAYMENT_REQUIRED(402),

    //{@code 403 Forbidden}
    FORBIDDEN(403),

    //{@code 404 Not Found}
    NOT_FOUND(404),

    //{@code 405 Method Not Allowed}
    METHOD_NOT_ALLOWED(405),

    //{@code 406 Not Acceptable}
    NOT_ACCEPTABLE(406),

    //{@code 407 Proxy Authentication Required}
    PROXY_AUTHENTICATION_REQUIRED(407),

    //{@code 408 Request Timeout}
    REQUEST_TIMEOUT(408),

    //{@code 409 Conflict}
    CONFLICT(409),

    //{@code 410 Gone}
    GONE(410),

    //{@code 411 Length Required}
    LENGTH_REQUIRED(411),

    //{@code 412 Precondition failed}
    PRECONDITION_FAILED(412),

    //{@code 413 Payload Too Large}
    PAYLOAD_TOO_LARGE(413),

    //{@code 414 URI Too Long}
    URI_TOO_LONG(414),

    //{@code 415 Unsupported Media Type}
    UNSUPPORTED_MEDIA_TYPE(415),

    //{@code 416 Requested Range Not Satisfiable}
    REQUESTED_RANGE_NOT_SATISFIABLE(416),

    //{@code 417 Expectation Failed}
    EXPECTATION_FAILED(417),

    //{@code 418 I'm a teapot}
    I_AM_A_TEAPOT(418),

    //{@code 422 Unprocessable Entity}
    UNPROCESSABLE_ENTITY(422),

    //{@code 423 Locked}
    LOCKED(423),

    //{@code 424 Failed Dependency}
    FAILED_DEPENDENCY(424),

    //{@code 425 Too Early}
    TOO_EARLY(425),

    //{@code 426 Upgrade Required}
    UPGRADE_REQUIRED(426),

    //{@code 428 Precondition Required}
    PRECONDITION_REQUIRED(428),

    //{@code 429 Too Many Requests}
    TOO_MANY_REQUESTS(429),

    //{@code 431 Request Header Fields Too Large}
    REQUEST_HEADER_FIELDS_TOO_LARGE(431),

    //{@code 451 Unavailable For Legal Reasons}
    UNAVAILABLE_FOR_LEGAL_REASONS(451),

    // --- 5xx Server Error ---

    //{@code 500 Internal Server Error}
    INTERNAL_SERVER_ERROR(500),

    //{@code 501 Not Implemented}
    NOT_IMPLEMENTED(501),

    //{@code 502 Bad Gateway}
    BAD_GATEWAY(502),

    //{@code 503 Service Unavailable}
    SERVICE_UNAVAILABLE(503),

    //{@code 504 Gateway Timeout}
    GATEWAY_TIMEOUT(504),

    //{@code 505 HTTP Version Not Supported}
    HTTP_VERSION_NOT_SUPPORTED(505),

    //{@code 506 Variant Also Negotiates}
    VARIANT_ALSO_NEGOTIATES(506),

    //{@code 507 Insufficient Storage}
    INSUFFICIENT_STORAGE(507),

     //{@code 508 Loop Detected}
    LOOP_DETECTED(508),
    //{@code 509 Bandwidth Limit Exceeded}
    BANDWIDTH_LIMIT_EXCEEDED(509),

    //{@code 510 Not Extended}
    NOT_EXTENDED(510),

    //{@code 511 Network Authentication Required}.
    NETWORK_AUTHENTICATION_REQUIRED(511);

    private int code;

    ResultCode(int code) {
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
