package administration.statements;

/**
 *
 * @author 
 */
public class StatementTr implements IStatement{
    
    // Класс сущности tr
    String tmp;

    public StatementTr(String tmp)
    {
        this.tmp = tmp;
    }

    public StatementTr()
    {
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
