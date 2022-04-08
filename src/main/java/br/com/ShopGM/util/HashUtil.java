package br.com.ShopGM.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

	//NÃO COMSEGUIMOS REVERTER O HASH
	//vai servir para as duas telas de cadastro que irá ter
	//hash serve para comparação
	//so tem um hash
	public static String hash(String palavra) {
		//"tempero" do hash
		String salt = "u@u@r@o";//usuario
		//adicionando o "tempero" à palavra
		palavra = salt + palavra;
		//gerando o hash
		String hash = Hashing.sha512().hashString(palavra, StandardCharsets.UTF_8).toString();//quando maior o numero melhor (numero de bits)
		//retorna o hash
		return hash;
	}
	
}