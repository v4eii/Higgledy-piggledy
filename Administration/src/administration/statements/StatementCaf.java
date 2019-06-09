package administration.statements;

/**
 *
 * @author v4e
 */
public class StatementCaf implements IStatement {
    
    // TODO: сущность Caf
    String tmp;

    public StatementCaf()
    {
    }

    public StatementCaf(String tmp)
    {
        this.tmp = tmp;
    }

    public String getTmp()
    {
        return tmp;
    }

    public void setTmp(String tmp)
    {
        this.tmp = tmp;
    }
    
    
}
