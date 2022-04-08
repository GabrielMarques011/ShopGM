package br.com.ShopGM.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;



public class FireBaseUtil {



	// VARIÁVEL PARA GUARDAR AS CREDENCIAIS DE ACESSO
	private Credentials credenciais;
	// VARIÁVE PARA ACESSAR E MANIPULAR O STORAGE, FIREBASE
	private Storage storage;
	// CONSTANTE PARA O NOME DO BUCKET
	private final String BUCKET_NAME = "shopgm-86f21.appspot.com";
	// CONSTATNTE PARA O PREFIXO DA URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	// CONSTANTE PARA O SUFIXO DA URL
	private final String SUFFIX = "?alt=media";
	// CONSTANTE PARA A URL
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;


	public FireBaseUtil() {
		// ACESSAR O ARQUIVO JSON COM A CHAVE PRIVADA
	
		Resource resource = new ClassPathResource("chave_do_firebase.json");
	
	
		try {
		// GERA UMA CREDENCIAL NO FIREBASE ATRAVÉS DA CHAVE DO ARQUIVO
		credenciais = GoogleCredentials.fromStream(resource.getInputStream());// fromStream, ESPERA RECEBER UM INPUT SCREEN
	
		// CRIA O STORAGE PARA MANIPULAR OS DADOS NO FIREBASE
		storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService(); //PRONTO PARA INSERIR E DELETAR ARQUIVOS
	
	
		} catch (IOException e) {
	
		throw new RuntimeException(e.getMessage());// JOGANDO O ERRO PRO FRONT
		}
	}
	
	// MÉTODO PARA EXTRAIR A EXTENSÃO DO ARQUIVO
	private String getExtesao(String nomeArquivo) {

	// EXTRAI O TRECHO DO ARQUIVO ONDE ESTÁ A EXTENSÃO
	return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}

	// MÉTODO QUE FAZ O UPLOAD
	public String upload(MultipartFile arquivo) {

	// GERAR UM NOME ALEATÓRIO, UM RANDOM DE STRING
	String nomeArquivo = UUID.randomUUID().toString() + getExtesao(arquivo.getOriginalFilename()); //UUID.randomUUID() , GERA UM HEXADECIMAL ALEATÓRIO

	return"";
	}
}