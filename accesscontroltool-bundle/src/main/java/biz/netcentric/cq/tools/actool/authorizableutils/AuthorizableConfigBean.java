/*
 * (C) Copyright 2015 Netcentric AG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package biz.netcentric.cq.tools.actool.authorizableutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import biz.netcentric.cq.tools.actool.dumpservice.AcDumpElement;
import biz.netcentric.cq.tools.actool.dumpservice.AcDumpElementVisitor;

public class AuthorizableConfigBean implements AcDumpElement {

    private String principalID;
    private String principalName;

    private String[] memberOf;
    String memberOfStringFromConfig;

    private String[] members;
    String membersStringFromConfig;

    private String description;
    private String path;
    private String password;

    private String migrateFrom;

    private boolean isGroup = true;
    private boolean isSystemUser = false;

    private String assertedExceptionString = null;

    public String getAssertedExceptionString() {
        return assertedExceptionString;
    }

    public void setAssertedExceptionString(final String assertedException) {
        assertedExceptionString = assertedException;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setMemberOfString(final String memberOfString) {
        memberOfStringFromConfig = memberOfString;
    }

    public void setMembersString(final String membersString) {
        membersStringFromConfig = membersString;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(final boolean isGroup) {
        this.isGroup = isGroup;
    }

    public boolean isSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(final boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public void memberOf(final boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setAuthorizableName(final String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalID() {
        return principalID;
    }

    public void setPrincipalID(final String principalID) {
        this.principalID = principalID;
    }

    public String[] getMemberOf() {
        return memberOf;
    }

    public boolean isMemberOfOtherGroups() {
        return memberOf != null;
    }

    public String getMemberOfStringFromConfig() {
        return memberOfStringFromConfig;
    }

    public String getMemberOfString() {
        if (memberOf == null) {
            return "";
        }

        final StringBuilder memberOfString = new StringBuilder();

        for (final String group : memberOf) {
            memberOfString.append(group).append(",");
        }
        return StringUtils.chop(memberOfString.toString());
    }

    public void setMemberOf(final String[] memberOf) {
        this.memberOf = memberOf;
    }

    public void setMemberOf(final List<String> memberOf) {
        if ((memberOf != null) && !memberOf.isEmpty()) {
            this.memberOf = memberOf.toArray(new String[memberOf.size()]);
        }
    }

    public void addMemberOf(final String member) {
        if (memberOf == null) {
            memberOf = new String[] { member };
            return;
        }
        final List<String> memberList = new ArrayList<String>();
        memberList.addAll(Arrays.asList(memberOf));
        if (!memberList.contains(member)) {
            memberList.add(member);
            memberOf = memberList.toArray(new String[memberList.size()]);
        }
    }

    public String getMembersStringFromConfig() {
        return membersStringFromConfig;
    }

    public String getMembersString() {
        if (members == null) {
            return "";
        }

        final StringBuilder membersString = new StringBuilder();

        for (final String group : members) {
            membersString.append(group).append(",");
        }
        return StringUtils.chop(membersString.toString());
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(final String[] members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getMigrateFrom() {
        return migrateFrom;
    }

    /** Set a group name, from which the users are taken over to this group. The group given is deleted after the run. This property is only
     * to be used temporarily (usually only included in one released version that travels all environments, once all groups are migrated the
     * config should be removed). If not set (the default) nothing happens. If the property points to a group that does not exist (anymore),
     * the property is ignored.
     * 
     * @param migrateFrom */
    public void setMigrateFrom(String migrateFrom) {
        this.migrateFrom = migrateFrom;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n" + "id: " + principalID + "\n");
        sb.append("name: " + principalName + "\n");
        sb.append("description: " + description + "\n");
        sb.append("path: " + path + "\n");
        sb.append("memberOf: " + getMemberOfString() + "\n");
        sb.append("members: " + getMembersString() + "\n");
        return sb.toString();
    }

    @Override
    public void accept(final AcDumpElementVisitor acDumpElementVisitor) {
        acDumpElementVisitor.visit(this);
    }
}
