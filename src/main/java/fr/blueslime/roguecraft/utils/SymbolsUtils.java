package fr.blueslime.roguecraft.utils;

public enum SymbolsUtils
{
    HEART("\u2764");
    
    private final String symbol;
    
    private SymbolsUtils(String symbol)
    {
        this.symbol = symbol;
    }
    
    public String getSymbol()
    {
        return this.symbol;
    }
}
