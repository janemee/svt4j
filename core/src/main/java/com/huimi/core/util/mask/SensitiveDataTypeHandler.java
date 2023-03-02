package com.huimi.core.util.mask;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.huimi.core.exception.BussinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用时相关表模型类需要 autoResultMap = true
 * \\@TableName(value = "xx_table",autoResultMap = true)
 * 敏感信息加解密处理
 */
@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SensitiveDataTypeHandler extends BaseTypeHandler<String> {

    private static String getEncKey() {
        return ("llwallet.busi.db.enckey"+"09876543210987654321");
    }

    /**
     * 非空字段加密
     *
     * @param preparedStatement
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) {
        try {
            if (StrUtil.isBlank(parameter)) {
                return;
            }
            String encrypt = encode(parameter);
            preparedStatement.setString(i, encrypt);
        } catch (Exception e) {
            log.error("typeHandler加密异常：" + e);
            throw new BussinessException("加密异常");
        }
    }

    public static String encode(String parameter) {

        return encode(parameter, getEncKey());
    }


    /**
     * 非空字段解密
     *
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String col = resultSet.getString(columnName);
        try {
            if (StrUtil.isBlank(col)) {
                return col;
            }
            String encrypt = decode(col, getEncKey());
            return encrypt;
        } catch (Exception e) {
            log.error("typeHandler解密异常：" + e);
            throw new BussinessException("解密异常");
        }
    }

    /**
     * 可空字段加密
     *
     * @param resultSet
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String col = resultSet.getString(columnIndex);
        try {
            if (StrUtil.isBlank(col)) {
                return col;
            }
            String encrypt = encode(col, getEncKey());
            return encrypt;
        } catch (Exception e) {
            log.error("typeHandler加密异常：" + e);
            throw new BussinessException("加密异常");
        }
    }

    /**
     * 可空字段解密
     *
     * @param callableStatement
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String col = callableStatement.getString(columnIndex);
        try {
            if (StrUtil.isBlank(col)) {
                return col;
            }
            String encrypt = decode(col, getEncKey());
            return encrypt;
        } catch (Exception e) {
            log.error("typeHandler解密异常：" + e);
            throw new BussinessException("解密异常");
        }
    }


    public static String encode(String src, String key) {
        byte[] bytes = ArrayUtil.sub(key.getBytes(StandardCharsets.UTF_8), 0, 16);
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, bytes);
        String enc = aes.encryptHex(src);
        return enc;
    }

    public String decode(String enc, String key) {
        byte[] bytes = ArrayUtil.sub(key.getBytes(StandardCharsets.UTF_8), 0, 16);
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, bytes);
        String src = aes.decryptStr(enc);
        return src;
    }

}
