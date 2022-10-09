package paymybuddy.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link_user")
public class LinkUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer linkId;
	
	@Column(name = "id_account")
	private Integer accountId;
	
	@Column(name = "id_friend")
	private Integer friendId;

	public LinkUser() {}

	public LinkUser(Integer linkId, Integer accountId, Integer friendId) {
		super();
		this.linkId = linkId;
		this.accountId = accountId;
		this.friendId = friendId;
	}

	@Override
	public String toString() {
		return "ID:"+linkId+", accountId:"+accountId+", friendId:"+friendId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkUser other = (LinkUser) obj;
		return Objects.equals(accountId, other.accountId) && Objects.equals(friendId, other.friendId);
	}

	public Integer getLinkId() {
		return linkId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public Integer getFriendId() {
		return friendId;
	}
}
