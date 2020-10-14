package com.tencent.ijk.media.player.annotations;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.CLASS)
public @interface AccessedByNative {
}
