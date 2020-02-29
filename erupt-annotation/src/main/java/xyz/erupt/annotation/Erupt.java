package xyz.erupt.annotation;

import xyz.erupt.annotation.config.ToMap;
import xyz.erupt.annotation.config.Volatile;
import xyz.erupt.annotation.constant.AnnotationConst;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_erupt.*;
import xyz.erupt.annotation.vola.PowerVolatile;

import java.beans.Transient;
import java.lang.annotation.*;

/**
 * @author liyuepeng
 * @date 2018-09-28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Erupt {

    String primaryKeyCol() default AnnotationConst.ID;

    @Transient
    String name();

    @Transient
    String desc() default AnnotationConst.EMPTY_STR;

    @Transient
    boolean loginUse() default true;

    @Volatile(PowerVolatile.class)
    Power power() default @Power;

    @ToMap(key = "code")
    RowOperation[] rowOperation() default {};

    @ToMap(key = "code")
    Drill[] drills() default {};

    @Transient
    Filter filter() default @Filter(condition = "");

    @Transient
    String orderBy() default "";

    @Transient
    Class<? extends DataProxy>[] dataProxy() default {};

    Tree tree() default @Tree(id = AnnotationConst.ID, label = AnnotationConst.LABEL);

    @ToMap(key = "key")
    KV[] param() default {};

    @Transient
    Tpl beforeHtml() default @Tpl;

    @Transient
    Tpl afterHtml() default @Tpl;

    Class<? extends Annotation> extra() default Annotation.class;
}
