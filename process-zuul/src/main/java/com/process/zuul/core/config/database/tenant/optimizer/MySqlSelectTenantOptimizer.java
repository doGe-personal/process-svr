package com.process.zuul.core.config.database.tenant.optimizer;

import cn.ocoop.framework.sql.TC;
import cn.ocoop.framework.sql.tenant.AbstractMysqlTenantASTVisitorAdapter;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlCharExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Lynn
 */
public class MySqlSelectTenantOptimizer extends AbstractMysqlTenantASTVisitorAdapter {

    public MySqlSelectTenantOptimizer(String tenantColumn, String tenantColumnType) {
        super(tenantColumn, tenantColumnType);
    }

    public boolean visit(MySqlSelectQueryBlock x) {
        super.visit(x);

        SQLExprTableSource sqlTableSource = recursiveGetOwner(x.getFrom());
        if (sqlTableSource == null) return true;

        SQLExpr where = x.getWhere();
        SQLExpr leftExpr;
        if (StringUtils.isNotBlank(sqlTableSource.getAlias())) {
            leftExpr = new SQLPropertyExpr(sqlTableSource.getAlias(), tenantColumn);
        } else {
            leftExpr = new SQLIdentifierExpr(tenantColumn);
        }

        SQLExpr rightExpr;
        if ("String".equals(tenantColumnType)) {
            rightExpr = new MySqlCharExpr((String) TC.get());
        } else {
            rightExpr = new SQLIntegerExpr((Number) TC.get());
        }
        SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr(
                leftExpr,
                SQLBinaryOperator.Equality,
                rightExpr
        );

        boolean conditionExist = false;
        if (where != null) {
            List<SQLObject> wheres = where.getChildren();
            if (wheres.contains(sqlBinaryOpExpr)) conditionExist = true;
        }
        if (!conditionExist) {
            x.addCondition(sqlBinaryOpExpr);
        }
        return true;
    }

    public SQLExprTableSource recursiveGetOwner(SQLTableSource sqlTableSource) {
        if (sqlTableSource instanceof SQLExprTableSource) {
            return (SQLExprTableSource) sqlTableSource;
        }

        if (sqlTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource tableSource = (SQLJoinTableSource) sqlTableSource;
            return recursiveGetOwner(tableSource.getLeft());
        }

        return null;
    }
}
