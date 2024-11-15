package com.ddam.damda.jwt.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.jwt.model.Token;
import com.ddam.damda.jwt.model.TokenExample;
import com.ddam.damda.jwt.model.mapper.TokenMapper;
@Service
public class TokenServiceImp implements TokenService {

	private TokenMapper tokenMapper;
	
	public TokenServiceImp(TokenMapper tokenMapper) {
		super();
		this.tokenMapper = tokenMapper;
	}
/*
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
 */
	@Override
	public List<Token> findAllAccessTokensByUser(Integer userId) {
		return tokenMapper.findAllAccessTokensByUser(userId);
	}

	@Override
	public Optional<Token> findByAccessToken(String token) {
		TokenExample tex=new TokenExample();
		tex.createCriteria().andAccessTokenEqualTo(token);
		List<Token> tokens= tokenMapper.selectByExample(tex);
		if(tokens!=null && tokens.size()>0) {
			return Optional.ofNullable(tokens.get(0));
		}else return Optional.ofNullable(null);
	}

	@Override
	public Optional<Token> findByRefreshToken(String token) {
		TokenExample tex=new TokenExample();
		tex.createCriteria().andRefreshTokenEqualTo(token);
		List<Token> tokens= tokenMapper.selectByExample(tex);
		if(tokens!=null && tokens.size()>0) {
			return Optional.ofNullable(tokens.get(0));
		}else return Optional.ofNullable(null);
	}

	@Override
	public void save(Token token) {
		TokenExample tex=new TokenExample();
		token.setId(null);
		tokenMapper.insertSelective(token);
	}

	@Override
	@Transactional
	public void saveAll(List<Token> validTokens) {
		if(validTokens!=null && validTokens.size()>0) {
			for (int i = 0; i < validTokens.size(); i++) {
				TokenExample tex=new TokenExample();
				tex.createCriteria().andIdEqualTo(validTokens.get(i).getId());
				Token tt=new Token();
				tt.setIsLoggedOut(true);
				//tt.setId(validTokens.get(i).getId());
				tokenMapper.updateByExampleSelective(tt, tex);
			}
			//tokenMapper.insertSelective(validTokens.get(i));
		}
	}
	@Override
	public void updatgDsave(Token storedToken) {
		TokenExample tex=new TokenExample();
		Token aaa=new Token();
		aaa.setId(storedToken.getId());
		aaa.setIsLoggedOut(false);
		tex.createCriteria().andIdEqualTo(storedToken.getId());
		tokenMapper.updateByExample(storedToken,tex);
	}

}
