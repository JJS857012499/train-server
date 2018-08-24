package top.pppppap.train;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-20 下午 20:53
 */

public class Station {
    private String name;
    private String shortSpell1;
    private String shortSpell2;
    private String longSpell;
    private String no;
    private String shorthand;

    @Override
    public String toString() {
        return name + '|' + shortSpell1 + '|' + shortSpell2 + '|' + longSpell + '|' + no + '|' + shorthand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortSpell1() {
        return shortSpell1;
    }

    public void setShortSpell1(String shortSpell1) {
        this.shortSpell1 = shortSpell1;
    }

    public String getShortSpell2() {
        return shortSpell2;
    }

    public void setShortSpell2(String shortSpell2) {
        this.shortSpell2 = shortSpell2;
    }

    public String getLongSpell() {
        return longSpell;
    }

    public void setLongSpell(String longSpell) {
        this.longSpell = longSpell;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getShorthand() {
        return shorthand;
    }

    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }
}
