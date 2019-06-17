package administration.statements;

import administration.beans.DBBean;
import administration.entity.GeneralTrade;
import javafx.scene.image.Image;

/**
 *
 * @author 
 */
public class StatementTr implements IStatement{
    
    private GeneralTrade statement;
    private Image INN, OGRN;

    public StatementTr(GeneralTrade statement, Image INN, Image OGRN)
    {
        this.statement = statement;
        this.INN = INN;
        this.OGRN = OGRN;
    }
    
    // TMP
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

    public Image getINN()
    {
        return INN;
    }

    public Image getOGRN()
    {
        return OGRN;
    }
    
}
