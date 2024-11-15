package com.ddam.damda.jwt.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ddam.damda.jwt.model.Token;
import com.ddam.damda.jwt.model.TokenExample;

public interface TokenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    long countByExample(TokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int deleteByExample(TokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int insert(Token row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int insertSelective(Token row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    List<Token> selectByExampleWithRowbounds(TokenExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    List<Token> selectByExample(TokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    Token selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") Token row, @Param("example") TokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") Token row, @Param("example") TokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Token row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Token row);

	List<Token> findAllAccessTokensByUser(Integer userId);
}