<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:android="http://schemas.android.com/apk/res/android"
                version="2.0">

    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="mergeFile"/>
    <xsl:variable name="mergeXml" select="document($mergeFile)"/>

    <xsl:variable name="ufs" select="$mergeXml/manifest/uses-feature"/>
    <xsl:variable name="ups" select="$mergeXml/manifest/uses-permission"/>
    <xsl:variable name="appactives" select="$mergeXml/manifest/application/activity"/>
    <xsl:variable name="attr_manifest" select="$mergeXml/manifest/@*"/>

    <xsl:variable name="attr_manifest_string" >
        <xsl:for-each select="$attr_manifest">
            <xsl:value-of select="concat('-',name(current()))"/>
        </xsl:for-each>
    </xsl:variable>



    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="manifest">

        <xsl:copy>

            <xsl:apply-templates select="@*[not(contains($attr_manifest_string,name()))]"/>

            <xsl:apply-templates select="$attr_manifest"/>

            <xsl:for-each select="/manifest/*">
                <xsl:if test="not(contains(name(),'uses-permission') or contains(name(),'application') or contains(name(),'uses-feature'))">
                    <xsl:copy-of select="current()"/>
                </xsl:if>
            </xsl:for-each>

            <xsl:for-each select="$mergeXml/manifest/*">
                <xsl:if test="not(contains(name(),'uses-permission') or contains(name(),'application') or contains(name(),'uses-feature'))">
                    <xsl:copy-of select="current()"/>
                </xsl:if>
            </xsl:for-each>

            <xsl:copy-of select="uses-feature[not(@name = $ufs/@android:name)]"/>
            <xsl:copy-of select="uses-permission[not(@name = $ups/@android:name)]"/>

            <xsl:copy-of select="$ufs"/>
            <xsl:copy-of select="$ups"/>

            <xsl:apply-templates select="application"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="application">
        <xsl:copy>
            <xsl:copy-of select="current()/@*[current()/@* = $mergeXml/manifest/application/@*]"/>
            <xsl:copy-of select="$mergeXml/manifest/application/@*"/>
            <xsl:copy-of select="current()/activity[not(@name=$appactives/android:name)]"/>
            <xsl:copy-of select="current()/node()"/>
            <xsl:copy-of select="$mergeXml/manifest/application/node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
