package Connect;

/*Token class
 * Basically is making disk i.e.'X' and 'O' which is then inserted into the board*/

public class Token {

    char disk;
    int value;

    Token(char disk) {
        this.disk = disk;
        //this.value = value;
    }

    char getDisk() {
        return this.disk;
    }

    int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Character.toString(disk);
    }
}
