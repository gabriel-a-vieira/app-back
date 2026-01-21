package utils.model.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class HibernateTenantFilter extends OncePerRequestFilter {

    private final TenantFilterEnabler tenantFilterEnabler;

    @Autowired
    public HibernateTenantFilter(TenantFilterEnabler tenantFilterEnabler) {
        this.tenantFilterEnabler = tenantFilterEnabler;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Method method = getMethodFromRequest(request);

        tenantFilterEnabler.enable(method);  // Ativa ou desativa o filtro com base na anotação

        try {
            filterChain.doFilter(request, response);
        } finally {
            tenantFilterEnabler.disable();  // Desativa o filtro após a execução
        }

    }

    private Method getMethodFromRequest(HttpServletRequest request) {

        String methodName = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

        try {
            return this.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

}



