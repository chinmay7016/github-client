package com.jraska.appsize.diff

import com.jraska.appsize.Component
import kotlin.math.absoluteValue

object AppSizeDiffPrinter {
  private const val DIVIDER_SIZE = 100

  fun asReadableString(appSizeDiff: AppSizeDiff): String {
    val builder = StringBuilder()

    builder.addDivider("App Size Diff")
    builder.addHeader(appSizeDiff)
    builder.addDivider("Added")
    builder.addDifferentComponents(appSizeDiff.addedComponents, "+ ")
    builder.addDivider("Removed")
    builder.addDifferentComponents(appSizeDiff.removedComponents, "- ")
    builder.addDivider("Modified")
    builder.addComponentDiffs(appSizeDiff.componentDiffs)

    return builder.toString()
  }

  private fun StringBuilder.addDivider(title: String = "") {
    val dividerDashes = (DIVIDER_SIZE - title.length) / 2

    repeat(dividerDashes) { append("-") }
    append(title)
    repeat(dividerDashes) { append("-") }

    if (title.length % 2 == 1) {
      append("-")
    }

    appendLine()
  }

  private fun StringBuilder.addHeader(appSizeDiff: AppSizeDiff) {
    append("From version: ").append(appSizeDiff.base.version)
    append(" to version: ").append(appSizeDiff.branch.version).appendLine()

    append("Total app size diff: ").appendLine()
    append(appSizeDiff.sizeDiff).appendLine()
  }

  private fun StringBuilder.addDifferentComponents(components: List<Component>, prefix: String) {
    components.sortedByDescending { it.size.downloadSizeBytes }.forEach {
      append(prefix).append(it.name).append(" ").append(it.size).appendLine()
    }
  }

  private fun StringBuilder.addComponentDiffs(componentDiffs: List<ComponentDiff>) {
    componentDiffs.sortedByDescending { it.sizeDiff.downloadSizeBytes.absoluteValue }.forEach {
      append(it.base.name).append(" ").append(it.sizeDiff).appendLine()
    }
  }
}
