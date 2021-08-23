package Models;

public class Roles {
        public int roleId;
        public String nameRole;

        public Roles(String nameRole) {
                this.nameRole = nameRole;
        }
        public Roles(int roleId,String nameRole) {
                this.roleId = roleId;
                this.nameRole = nameRole;
        }

}
