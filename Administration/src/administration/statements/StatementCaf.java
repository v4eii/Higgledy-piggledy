package administration.statements;

import administration.beans.DBBean;
import administration.entity.GeneralCafe;
import javafx.scene.image.Image;

/**
 *
 * @author v4e
 */
public class StatementCaf implements IStatement {
    
    private GeneralCafe statement;
    private Image INN, OGRN;

    public StatementCaf()
    {
    }

    public StatementCaf(GeneralCafe statement, Image INN, Image OGRN)
    {
        this.statement = statement;
        this.INN = INN;
        this.OGRN = OGRN;
    }
    
    // TMP
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

    public Image getINN()
    {
        return INN;
    }

    public Image getOGRN()
    {
        return OGRN;
    }
}
