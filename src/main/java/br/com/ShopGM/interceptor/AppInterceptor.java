package br.com.ShopGM.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.ShopGM.annotation.Publico;

//MARCANDO QUE ESSA CLASS É UM COMPONENTE
@Component
public class AppInterceptor implements HandlerInterceptor{//HandlerInterceptor é uma interface
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//REQUEST INFORMAÇÕES DA REQUISIÇÃO
		//RESPONCE É PARA REDIRECIONAR
		//HANDLER É PARA SABER SE EXISTE AQUILO QUE O USUARIO ESTÁ PROCURANDO
		
		//VARIAVEL PARA OBTER A URI
		String uri = request.getRequestURI();
		
		//VARIAVEL PARA A SESSÃO
		HttpSession session = request.getSession();
		
		//SE FOR PAGINA DE ERRO LIBERA
		if (uri.startsWith("/error")) {
			return true;
		}

		//VERIFICAR SE O HANDLER É UM HANDLERMETHOD, O QUE INDICA QUE ELE ESTÁ CHAMANDO O METODO EM ALGUM CONTROLER
		if (handler instanceof HandlerMethod) {
			
			//CASTING DE OBJETC PARA HANDLERMETHODS
			HandlerMethod metodo = (HandlerMethod) handler;
			
			if (uri.startsWith("/api")) {
				return true;
			}else{
			
			//VERIFICANDO SE ELE É PUBLICO
			if (metodo.getMethodAnnotation(Publico.class) != null ) {
				return true;
			}
			
			//VERIFICA SE EXISTE USER LOGADO
			if (session.getAttribute("usuarioLogado") != null) {
				return true;
			}
			
			//REDIRECIONA PARA PAGINA INCIAL
			response.sendRedirect("/formLogin");
			return false;
			
			}
		}
		
		return true;
	}

}
