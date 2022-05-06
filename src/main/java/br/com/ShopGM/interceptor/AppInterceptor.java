package br.com.ShopGM.interceptor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.ShopGM.annotation.Privado;
import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.rest.UsuarioRestController;

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

				//variavel para o token
				String token = null;
				
				//verifica se é um metodo privado
				if (metodo.getMethodAnnotation(Privado.class) != null) {
					
					try {
						
						//se o metodo for privado recupera o token
						token = request.getHeader("Authorization");
						//buscando o algoritmo do usuario
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						//objeto para verificar o token
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
						//decodifica o Token
						DecodedJWT jwt = verifier.verify(token);
						//recupera os dados do payload (claims são valores que vem no payload)
						Map<String, Claim> claims = jwt.getClaims();
						System.out.println(claims.get("name"));
						
						return true;
					
					} catch (Exception e) {
						
						e.printStackTrace();
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						
						return false;
					}
					
				}
				
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
