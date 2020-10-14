package com.tencent.ijk.media.player.annotations;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface CalledByNative {
    String value() default "";
}
