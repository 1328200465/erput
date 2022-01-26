package xyz.erupt.toolkit.handler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.core.util.EruptAssert;
import xyz.erupt.toolkit.cache.EruptCache;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YuePeng
 * date 2021/01/03 18:00
 */
@Component
public class SqlChoiceFetchHandler implements ChoiceFetchHandler {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final EruptCache<List<VLModel>> eruptCache = EruptCache.factory();

    @Override
    public List<VLModel> fetch(String[] params) {
        EruptAssert.notNull(params, SqlChoiceFetchHandler.class.getSimpleName() + " → params not found");
        return eruptCache.getAndSet(SqlChoiceFetchHandler.class.getName() + ":" + params[0],
                params.length == 2 ? Long.parseLong(params[1]) : 3000,
                (key) -> jdbcTemplate.query(params[0], (rs, i) -> {
                    if (rs.getMetaData().getColumnCount() == 1) {
                        return new VLModel(rs.getString(1), rs.getString(1));
                    } else {
                        return new VLModel(rs.getString(1), rs.getString(2));
                    }
                }));
    }

}
