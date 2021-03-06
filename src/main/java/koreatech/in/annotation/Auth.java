package koreatech.in.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    public enum Role { ADMIN, USER, OWNER, NONE }
    public enum Authority { USER, CALLVAN, LAND, COMMUNITY, SHOP, VERSION, MARKET, CIRCLE, LOST, SURVEY, BCSDLAB, EVENT, NONE }

    public Role role() default Role.NONE;
    public Authority authority() default Authority.NONE;
}
