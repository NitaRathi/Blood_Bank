public class Blood_Inventory
{
    private String BloodGroup;
    private int Units;

    public Blood_Inventory() {
        this.BloodGroup = "";
        Units = 0;
    }

    public Blood_Inventory(String BloodGroup, int units) {
        this.BloodGroup = BloodGroup;
        Units = units;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String BloodGroup) {
        this.BloodGroup = BloodGroup;
    }

    public int getUnits() {
        return Units;
    }

    public void setUnits(int units) {
        Units = units;
    }
}