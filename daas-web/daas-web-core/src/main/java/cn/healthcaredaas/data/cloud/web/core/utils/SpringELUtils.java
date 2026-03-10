package cn.healthcaredaas.data.cloud.web.core.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

/**
 * @Description: TOOD
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/8 14:03
 * @Modify：
 */
public class SpringELUtils {

    private static final Logger log = LoggerFactory.getLogger(SpringELUtils.class);

    // Spring EL 表达式解析器
    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /*
     * 解析Spring EL 表达式
     */
    public static String parseElExpression(String expression, JoinPoint joinPoint) {
        if (expression == null || expression.trim().isEmpty()) {
            return expression;
        }

        try {
            // 如果不包含 EL 表达式标识符，直接返回原字符串
            if (!expression.contains("#") && !expression.contains("${")) {
                return expression;
            }

            // 创建 EL 表达式上下文
            MethodBasedEvaluationContext context = createEvaluationContext(joinPoint);

            // 解析并计算表达式
            Expression exp = parser.parseExpression(expression);
            Object value = exp.getValue(context);

            return value != null ? value.toString() : "";

        } catch (Exception e) {
            log.warn("解析 Spring EL 表达式失败: {}, 使用原始值", expression, e);
            return expression;
        }
    }

    /**
     * 创建包含方法参数的 Spring EL 上下文
     */
    private static MethodBasedEvaluationContext createEvaluationContext(JoinPoint joinPoint) {
        // 获取方法签名和参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        // 创建 EL 表达式上下文
        MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(
                joinPoint.getTarget(), method, args, parameterNameDiscoverer);

        // 设置方法参数到上下文中
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }

        return context;
    }
}
