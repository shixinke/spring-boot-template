package com.github.shixinke.spring.boot.template.handler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.shixinke.utils.web.annotation.cat.Nullable;
import com.shixinke.utils.web.common.Errors;
import com.shixinke.utils.web.common.ResponseDTO;
import com.shixinke.utils.web.exception.CommonException;
import com.shixinke.utils.web.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @author shixinke
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO handle(Exception ex) {
        String msg = null;
        int code = -1;
        if (ex instanceof ValidateException) {
            msg = ex.getMessage();
            code = ((ValidateException) ex).getCode();
            log.error("parameter validate failed:{}", ex);
        } else if (ex instanceof BlockException) {
            msg = Errors.FLOW_ERROR.getMessage();
            code = Errors.FLOW_ERROR.getCode();
            log.error("flow control over the :{}", ex);
        } else if (ex instanceof CommonException) {
            code = ((CommonException) ex).getCode();
            msg = ex.getMessage();
            log.error("common exception :{}", ex);
        } else {
            code = Errors.SERVER_ERROR.getCode();
            log.error("get data failed:{}", ex);
        }
        return ResponseDTO.error(code, msg);
    }

}