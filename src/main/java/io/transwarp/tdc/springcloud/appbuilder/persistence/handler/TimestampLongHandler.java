package io.transwarp.tdc.springcloud.appbuilder.persistence.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * TimestampLongHandler
 * Author: zhe.jiang
 * Desc:
 *
 * Change Log:
 * 8/5/16 - created by zhe.jiang
 * 1/23/17 - truncate precision to second level, not implemented, wait for upgrade of mysql
 */
public class TimestampLongHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new Timestamp(parameter));
    }

    @Override
    public Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        if (rs.wasNull())
            return null;
        return ts.getTime();
    }

    @Override
    public Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        if (rs.wasNull())
            return null;
        return ts.getTime();
    }

    @Override
    public Long getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp ts = cs.getTimestamp(columnIndex);
        if (cs.wasNull())
            return null;
        return ts.getTime();
    }
}
