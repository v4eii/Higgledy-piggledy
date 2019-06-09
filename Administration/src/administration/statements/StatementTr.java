package administration.statements;

import administration.beans.DBBean;
import administration.entity.GeneralTrade;

/**
 *
 * @author 
 */
public class StatementTr implements IStatement{
    
    private GeneralTrade statement;

    public StatementTr(GeneralTrade statement)
    {
        this.statement = statement;
    }

    public StatementTr()
    {
    }

    public GeneralTrade getStatement()
    {
        return statement;
    }
    
    @Override
    public String toString()
    {
        return DBBean.getInstance().getStatementJpaController().findStatement(statement.getIdUnion()).getOrg();
    }
    
}
