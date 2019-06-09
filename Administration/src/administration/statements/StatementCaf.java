package administration.statements;

import administration.beans.DBBean;
import administration.entity.GeneralCafe;

/**
 *
 * @author v4e
 */
public class StatementCaf implements IStatement {
    
    private GeneralCafe statement;

    public StatementCaf()
    {
    }

    public StatementCaf(GeneralCafe statement)
    {
        this.statement = statement;
    }

    public GeneralCafe getStatement()
    {
        return statement;
    }

    @Override
    public String toString()
    {
        return DBBean.getInstance().getStatementJpaController().findStatement(statement.getIdUnion()).getOrg();
    }
    
    
    
    
}
