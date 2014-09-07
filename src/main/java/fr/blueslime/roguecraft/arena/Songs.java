package fr.blueslime.roguecraft.arena;

public enum Songs
{
    POSITIVEFORCE("PositiveForce.nbs"),
    VANILLATWILIGHT("VanillaTwilight.nbs"),
    MADWORLD("MadWorld.nbs"),
    SACRIFICIAL("Sacrificial.nbs");
    
    private final String fileName;
    
    private Songs(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getFileName()
    {
        return this.fileName;
    }
}
