/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wisii.wisedoc.view.ui.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.view.action.AddTableCellLeftAction;
import com.wisii.wisedoc.view.action.AddTableCellRightAction;
import com.wisii.wisedoc.view.action.AddTableFooterAction;
import com.wisii.wisedoc.view.action.AddTableHeaderAction;
import com.wisii.wisedoc.view.action.AddTableRowAboveAction;
import com.wisii.wisedoc.view.action.AddTableRowBelowAction;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.action.BrowseAction;
import com.wisii.wisedoc.view.action.ContactAction;
import com.wisii.wisedoc.view.action.CopyAction;
import com.wisii.wisedoc.view.action.CutAction;
import com.wisii.wisedoc.view.action.DefaultAction;
import com.wisii.wisedoc.view.action.DeleteTableAction;
import com.wisii.wisedoc.view.action.DeleteTableCellAction;
import com.wisii.wisedoc.view.action.DeleteTableFooterAction;
import com.wisii.wisedoc.view.action.DeleteTableHeaderAction;
import com.wisii.wisedoc.view.action.DeleteTableRowAction;
import com.wisii.wisedoc.view.action.EditModelAction;
import com.wisii.wisedoc.view.action.EditStructureAction;
import com.wisii.wisedoc.view.action.ExplortDataPathFileAction;
import com.wisii.wisedoc.view.action.ExplortHtmlXSLTFileAction;
import com.wisii.wisedoc.view.action.ExplortPSXSLTFileAction;
import com.wisii.wisedoc.view.action.ExplortStandardPSXSLTFileAction;
import com.wisii.wisedoc.view.action.ExplortStandardXSLTFileAction;
import com.wisii.wisedoc.view.action.ExplortXSLTFileAction;
import com.wisii.wisedoc.view.action.GeneratedRegistrationInformationAction;
import com.wisii.wisedoc.view.action.GroupAction;
import com.wisii.wisedoc.view.action.HtmlBrowseAction;
import com.wisii.wisedoc.view.action.InSertBlockContainerAction;
import com.wisii.wisedoc.view.action.InSertRelativeBlockContainerAction;
import com.wisii.wisedoc.view.action.InsertBlockAfterAction;
import com.wisii.wisedoc.view.action.InsertBlockBeforeAction;
import com.wisii.wisedoc.view.action.InsertCanvasAction;
import com.wisii.wisedoc.view.action.InsertChartAction;
import com.wisii.wisedoc.view.action.InsertCheckBoxAction;
import com.wisii.wisedoc.view.action.InsertDateTimeAction;
import com.wisii.wisedoc.view.action.InsertEllipseAction;
import com.wisii.wisedoc.view.action.InsertEncryptTextAction;
import com.wisii.wisedoc.view.action.InsertImageAction;
import com.wisii.wisedoc.view.action.InsertLineAction;
import com.wisii.wisedoc.view.action.InsertNumberTextAction;
import com.wisii.wisedoc.view.action.InsertPSTotalPageNumberAction;
import com.wisii.wisedoc.view.action.InsertPageNumberAction;
import com.wisii.wisedoc.view.action.InsertPagesequenceAction;
import com.wisii.wisedoc.view.action.InsertRectangleAction;
import com.wisii.wisedoc.view.action.InsertSpecialCharactorAction;
import com.wisii.wisedoc.view.action.InsertTableAction;
import com.wisii.wisedoc.view.action.InsertTotalPageNumberAction;
import com.wisii.wisedoc.view.action.InsertWordArtTextAction;
import com.wisii.wisedoc.view.action.InsertZiMobanAction;
import com.wisii.wisedoc.view.action.LoadRegistrationDocumentAction;
import com.wisii.wisedoc.view.action.LoadSQLStructureAction;
import com.wisii.wisedoc.view.action.LoadStructureAction;
import com.wisii.wisedoc.view.action.MergeTableCellAction;
import com.wisii.wisedoc.view.action.ModelManegemnetAction;
import com.wisii.wisedoc.view.action.NewDocumentAction;
import com.wisii.wisedoc.view.action.OpenFileAction;
import com.wisii.wisedoc.view.action.OpenModelAction;
import com.wisii.wisedoc.view.action.PasteAction;
import com.wisii.wisedoc.view.action.PasteTextAction;
import com.wisii.wisedoc.view.action.PasteWithOutBindNode;
import com.wisii.wisedoc.view.action.ReMoveStructureAction;
import com.wisii.wisedoc.view.action.RedoAction;
import com.wisii.wisedoc.view.action.SaveAsWSDFileAction;
import com.wisii.wisedoc.view.action.SaveAsWSDMFileAction;
import com.wisii.wisedoc.view.action.SaveAsWSDTFileAction;
import com.wisii.wisedoc.view.action.SaveAsWSDXFileAction;
import com.wisii.wisedoc.view.action.SaveFileAction;
import com.wisii.wisedoc.view.action.SplitTableCellAction;
import com.wisii.wisedoc.view.action.UnGroupAction;
import com.wisii.wisedoc.view.action.UndoAction;
import com.wisii.wisedoc.view.action.UpdateTableContentsAction;
import com.wisii.wisedoc.view.action.positioninline.InsertPositionInline1Action;
import com.wisii.wisedoc.view.action.positioninline.InsertPositionInline_AAction;
import com.wisii.wisedoc.view.action.positioninline.InsertPositionInline_IAction;
import com.wisii.wisedoc.view.action.positioninline.InsertPositionInlineaAction;
import com.wisii.wisedoc.view.action.positioninline.InsertPositionInlineiAction;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.actions.AfterBorderAction;
import com.wisii.wisedoc.view.ui.actions.AllBorderAction;
import com.wisii.wisedoc.view.ui.actions.BackgroundImageSetAction;
import com.wisii.wisedoc.view.ui.actions.BeforeBorderAction;
import com.wisii.wisedoc.view.ui.actions.EndBorderAction;
import com.wisii.wisedoc.view.ui.actions.NoneBorderAction;
import com.wisii.wisedoc.view.ui.actions.RemoveBackGroundImageAction;
import com.wisii.wisedoc.view.ui.actions.StartBorderAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeAddCheckSumAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeCleanStyleAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeColumnsAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeECLevelAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeFontFamilyAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeFontSizeAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeHeigthAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeInputContentAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeMakeUCCAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeMaxColumnsAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeMaxRowsAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeMinColumnsAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeMinRowsAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeOrientationAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodePrintTextAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeQuietHorizontalAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeQuietVerticalAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeStringAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeSubSetAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTextBlockAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTextCharSpaceAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeType128DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeType25DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeType39Action;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeType39DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypeEAN128DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypeEAN13DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypeEAN8DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypePDF417DefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypeUPCADefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeTypeUPCEDefaultAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeWideToNarrowAction;
import com.wisii.wisedoc.view.ui.actions.barcode.BarcodeWidthAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerBPDAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerBPDAutoAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerContentTreatAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerEndPositionAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerIPDAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerIPDAutoAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerLeftPositionAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerOverFlowHiddenAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerOverFlowAutoFontSizeAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerOverFlowVisibleAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerPaddingBottomAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerPaddingLeftAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerPaddingRightAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerPaddingTopAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerStartPositionAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerTopPositionAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.RemoveBlockContainerContentTreatAction;
import com.wisii.wisedoc.view.ui.actions.blockcontainer.SizeByContentAction;
import com.wisii.wisedoc.view.ui.actions.buttons.RemoveButtonsAction;
import com.wisii.wisedoc.view.ui.actions.buttons.SetButtonsAction;
import com.wisii.wisedoc.view.ui.actions.chart.ChartLayerAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartAduatePrecisionAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartAutoAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartBPDAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartBackAlphaAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartBackGroundColorAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartDepth3DAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartFillPictureAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartFrontAlphaAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartIPDAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartIndicatorAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartMarginAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartMaxAduateAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartMinAduateAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartOffsetAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartPieOriationAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartPieShowFactValueAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartPieShowLabelAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartPieShowPercentAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartSeriesCountAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartSeriesLabelDegreeAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartSeriesTextAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShow3DAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowAxisXAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowAxisYAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowBaseLineAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowNullValueAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowValueAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartShowZeroValueAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartStartDegreeAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartStepAduateAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartTitleAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartTypeAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartValueCountAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocChartValueTextAction;
import com.wisii.wisedoc.view.ui.actions.chart.WisedocCharttOrientation;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxBoxStyleAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxBoxTickMarkAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxCreatGroupUiAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxIsSelectedAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxSelectValueAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxSetGroupUiAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.CheckBoxUnselectValueAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.IsEditableAction;
import com.wisii.wisedoc.view.ui.actions.checkbox.SetBoxStyleLayerAction;
import com.wisii.wisedoc.view.ui.actions.condition.RemoveBlockConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.RemoveInlineConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.RemoveObjectCondtionAction;
import com.wisii.wisedoc.view.ui.actions.condition.RemovePageSequenceConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.SetBlockConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.SetInlineConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.SetObjectConditionAction;
import com.wisii.wisedoc.view.ui.actions.condition.SetPageSequenceConditionAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.RemoveDatatreatTransformTableAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.RemoveDatatreatXpathPositionAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.RemoveSimpleDatatreatTransformTableAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.SetDatatreatTransformTableAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.SetDatatreatXpathPositionAction;
import com.wisii.wisedoc.view.ui.actions.datatreat.SetSimpleDatatreatTransformTableAction;
import com.wisii.wisedoc.view.ui.actions.datetime.DateInputSetAction;
import com.wisii.wisedoc.view.ui.actions.datetime.DateOutputSetAction;
import com.wisii.wisedoc.view.ui.actions.datetime.InputSeperatorAction;
import com.wisii.wisedoc.view.ui.actions.datetime.OutputSeperatorAction;
import com.wisii.wisedoc.view.ui.actions.datetime.TimeInputSetAction;
import com.wisii.wisedoc.view.ui.actions.datetime.TimeOutputSetAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.RemoveBlockDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.RemoveInlineDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.RemoveObjectDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.RemovePagesequenceDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.RemoveWisedocumentDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.SetBlockDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.SetInlineDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.SetObjectDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.SetPagesequenceDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.dynamicstyle.SetWisedocumentDynamicStyleAction;
import com.wisii.wisedoc.view.ui.actions.edit.ConnwithAction;
import com.wisii.wisedoc.view.ui.actions.edit.DateTypeAction;
import com.wisii.wisedoc.view.ui.actions.edit.DefaultValueAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditAppearanceAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditAuthorityAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditHeightAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditHintAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditIsReloadAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditTypeAction;
import com.wisii.wisedoc.view.ui.actions.edit.EditWidthAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputDataSourceAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputFilterAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputFilterMsgAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputMultilineAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputTypeAction;
import com.wisii.wisedoc.view.ui.actions.edit.InputWrapAction;
import com.wisii.wisedoc.view.ui.actions.edit.OnBlurValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.OnEditValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.OnResultValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.PopupBrowserInfoAction;
import com.wisii.wisedoc.view.ui.actions.edit.RemoveConnwithAction;
import com.wisii.wisedoc.view.ui.actions.edit.RemoveOnEditValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.RemoveOnResultValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.RemoveOnblurValidationAction;
import com.wisii.wisedoc.view.ui.actions.edit.RemoveSelectNextAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectEditableAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectInfoAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectLinesAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectMultipleAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectShowListAction;
import com.wisii.wisedoc.view.ui.actions.edit.SelectTypeAction;
import com.wisii.wisedoc.view.ui.actions.edit.SetSelectNameAction;
import com.wisii.wisedoc.view.ui.actions.edit.SetSelectNextAction;
import com.wisii.wisedoc.view.ui.actions.encrypttext.SetEncryptAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.GraphicIsEditableAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.ReSetAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetAlphaAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetContentHeightAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetContentWidthAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetDataTypeAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetLayerAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetScalingAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetSizeTypeAction;
import com.wisii.wisedoc.view.ui.actions.externalgraphic.SetSrcURLAction;
import com.wisii.wisedoc.view.ui.actions.group.RemoveBlockGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.RemoveCurrentGroupGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.RemoveCurrentObjectGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.RemoveInlineGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.RemovePageSequenceGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.RemoveWisedocumentGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.SetBlockGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.SetCurrentGroupGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.SetCurrentObjectGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.SetInlineGroupAction;
import com.wisii.wisedoc.view.ui.actions.group.SetWisedocumentGroupAction;
import com.wisii.wisedoc.view.ui.actions.index.IndexLevelAction;
import com.wisii.wisedoc.view.ui.actions.index.IndexLevelCancelAction;
import com.wisii.wisedoc.view.ui.actions.index.InsertTableContentsAction;
import com.wisii.wisedoc.view.ui.actions.numbertext.ChineseNumberSetAction;
import com.wisii.wisedoc.view.ui.actions.numbertext.BaifenbiSetAction;
import com.wisii.wisedoc.view.ui.actions.numbertext.SetIsChineseAction;
import com.wisii.wisedoc.view.ui.actions.numbertext.ThousandSeparatorSetAction;
import com.wisii.wisedoc.view.ui.actions.numbertext.WeishuSetAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.AddAllPageSequenceLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.AddFirstLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.AddLastLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.AddOddAndEvenLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageAddSequenceMasterLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageAddSimpleMasterLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyBackgroundImageRepeatAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyBackgroundPositionHorizontal;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyBackgroundPositionVertical;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyMarginAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageBodyTextDirectionAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageColumnCountAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageColumnGapAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageEditSequenceMasterLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageEditSimpleMasterLayoutAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageMarginAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageOrientationAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PagePaperSizeAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PagePaperSizeInputAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageShowHeaderAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.PageTextDirectionAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.RemoveBodyBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.SetComplexPageSequenceMasterAction;
import com.wisii.wisedoc.view.ui.actions.pagelayout.SetSimplePageSequenceMasterAction;
import com.wisii.wisedoc.view.ui.actions.pagesequence.DeletePageSequenceAction;
import com.wisii.wisedoc.view.ui.actions.pagesequence.SetForcePageCountAction;
import com.wisii.wisedoc.view.ui.actions.pagesequence.SetFormatAction;
import com.wisii.wisedoc.view.ui.actions.pagesequence.SetInitialPageNumberAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBackgroundColorLayerAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBorderColorAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBorderSettingAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBorderStyleAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBorderWidthAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBreakAfterAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphBreakBeforeAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphEndIndentAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphKeepTogetherAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphKeepWithNextAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphKeepWithPreviousAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineHeightAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineHeightStrategyAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace100Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace115Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace150Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace200Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace250Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpace300Action;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpaceAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpaceAfterAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpaceBeforeAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphLineSpaceInputAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphSpecialIndentAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphSpecialIndentInputAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphStartIndentAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphTextAlignCenterAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphTextAlignEndAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphTextAlignJustifyAction;
import com.wisii.wisedoc.view.ui.actions.paragraph.ParagraphTextAlignStartAction;
import com.wisii.wisedoc.view.ui.actions.qianzhang.InsertQianzhangAction;
import com.wisii.wisedoc.view.ui.actions.qianzhang.SetSrcAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterBackgroundImageRemoveAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterDeleteContentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterExtentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterOverflowAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterPrecedenceAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterReferenceOrientationAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionAfterWritingModeAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeBackgroundImageRemoveAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeDeleteContentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeExtentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeOverflowAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforePrecedenceAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeReferenceOrientationAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionBeforeWritingModeAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndBackgroundImageRemoveAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndDeleteContentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndExtentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndOverflowAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndReferenceOrientationAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionEndWritingModeAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartBackgroundImageAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartBackgroundImageRemoveAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartDeleteContentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartExtentAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartOverflowAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartReferenceOrientationAction;
import com.wisii.wisedoc.view.ui.actions.regions.RegionStartWritingModeAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgBottomAlign;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgEqualHeight;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgEqualWidth;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicArrowEndStyle;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicArrowStartStyle;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicCanvasHeightAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicCanvasWidthAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFillColorAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFillOpacityAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFillPictureAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontCleanAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontColorAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontFamilyAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontItalicAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontLineThroughAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontSizeAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontUnderlineAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicFontWeightAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicHeightAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicLayerAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicLineColorAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicLineTypeAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicLineWidthAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicOpacityAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicOrientationAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicRectangleRx;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicRectangleRy;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicRotateAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicStrokeLineJoin;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicTextContentAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicWidthAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicXAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgGraphicYAction;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgHorizontalCenter;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgLeftAlign;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgRightAlign;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgTopAlign;
import com.wisii.wisedoc.view.ui.actions.svggraphic.SvgVerticalCenter;
import com.wisii.wisedoc.view.ui.actions.table.SizeTypeAction;
import com.wisii.wisedoc.view.ui.actions.table.TableBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellBottomPaddingAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellHeightAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellLeftPaddingAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellRightPaddingAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellTopPaddingAction;
import com.wisii.wisedoc.view.ui.actions.table.TableCellWidthAction;
import com.wisii.wisedoc.view.ui.actions.table.TableDisplayAlignAction;
import com.wisii.wisedoc.view.ui.actions.table.TableEndIndentAction;
import com.wisii.wisedoc.view.ui.actions.table.TableStartIndentAction;
import com.wisii.wisedoc.view.ui.actions.table.TableWidthAction;
import com.wisii.wisedoc.view.ui.actions.table.TableWidthInputAction;
import com.wisii.wisedoc.view.ui.actions.text.FontBackgroundColorAction;
import com.wisii.wisedoc.view.ui.actions.text.FontBackgroundColorLayerAction;
import com.wisii.wisedoc.view.ui.actions.text.FontBoldAction;
import com.wisii.wisedoc.view.ui.actions.text.FontCleanAction;
import com.wisii.wisedoc.view.ui.actions.text.FontColorAction;
import com.wisii.wisedoc.view.ui.actions.text.FontColorLayerAction;
import com.wisii.wisedoc.view.ui.actions.text.FontFamilyAction;
import com.wisii.wisedoc.view.ui.actions.text.FontItalicAction;
import com.wisii.wisedoc.view.ui.actions.text.FontLineThroughAction;
import com.wisii.wisedoc.view.ui.actions.text.FontOverlineAction;
import com.wisii.wisedoc.view.ui.actions.text.FontPositionAction;
import com.wisii.wisedoc.view.ui.actions.text.FontPositionInputAction;
import com.wisii.wisedoc.view.ui.actions.text.FontSizeAction;
import com.wisii.wisedoc.view.ui.actions.text.FontSpaceAction;
import com.wisii.wisedoc.view.ui.actions.text.FontSpaceInputAction;
import com.wisii.wisedoc.view.ui.actions.text.FontStretchAction;
import com.wisii.wisedoc.view.ui.actions.text.FontStyleAction;
import com.wisii.wisedoc.view.ui.actions.text.FontSubAction;
import com.wisii.wisedoc.view.ui.actions.text.FontSuperAction;
import com.wisii.wisedoc.view.ui.actions.text.FontUnderlineAction;
import com.wisii.wisedoc.view.ui.actions.text.FontWordSpaceAction;
import com.wisii.wisedoc.view.ui.actions.text.FontWordSpaceInputAction;
import com.wisii.wisedoc.view.ui.actions.wisedoc.SetDBTypeAction;
import com.wisii.wisedoc.view.ui.actions.wisedoc.SetEditAction;
import com.wisii.wisedoc.view.ui.actions.wisedoc.SetLinefedTreatmentAction;
import com.wisii.wisedoc.view.ui.actions.wisedoc.SetWhiteSpaceCollapseAction;
import com.wisii.wisedoc.view.ui.actions.wisedoc.SetWhiteSpaceTreatmentAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetAlignmentAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetHeightAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetLetterSpaceAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetPathTypeAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetPathVisableAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetRotationAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetStartPositionAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.SetTextContentAction;
import com.wisii.wisedoc.view.ui.actions.wordarttext.setWidthAction;

/**
 * 所有界面的Action在这里登记ID，并把ActionID和真正的Action类对应起来
 * 所有创建的Action都应该在这里根据相应的类别创建自己的ActionID，并要把实际的类和ActionID放到actionMap中
 * 这里有个建议就是尽量让ActionID的名字和动作类的名字一样，这样容易检查；
 * 如：ActionID：Font.FAMILY_ACTION对应的动作类为：FontFamilyAction.class
 * 
 * @author 闫舒寰
 * @version 1.5 2008/11/19
 */
public enum ActionFactory
{

	AFACTORY;

	/**
	 * 保存所有action ID和实际action类动作的map
	 */
	private static Map<Enum<? extends ActionID>, Class<? extends BaseAction>> actionMap = new HashMap<Enum<? extends ActionID>, Class<? extends BaseAction>>();

	/**
	 * 目前被激活的actions
	 */
	private static Map<Enum<? extends ActionID>, BaseAction> activeActions = new HashMap<Enum<? extends ActionID>, BaseAction>();

	/**
	 * 用于标记action所发出的动作源
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum ActionCommandType
	{
		/**
		 * Ribbon界面所触发的动作
		 */
		RIBBON_ACTION,
		/**
		 * 对话框所出发的动作
		 */
		DIALOG_ACTION,
		/**
		 * 动态样式所触发的动作
		 */
		DYNAMIC_ACTION;
	}

	/**
	 * 用于标记动作的接口
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public interface ActionID
	{

		public ActionType getType();
	}

	/**
	 * 动作类型的枚举集合。 用户设置的属性有可能是一样的，但是动作类型会不一样。
	 * 比如border属性，对于inline和block的属性设定完全一样，设置类型是用于区分属性设置到什么上的。
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum ActionType
	{
		/**
		 * 未定义类别
		 */
		UNDEFINED,
		/**
		 * inline级别标记
		 */
		INLINE,

		/**
		 * block级别的标记
		 */
		BLOCK,

		/**
		 * layout级别标记，目前没有针对simple-page-master和region-body进行区分，
		 * 关于这两个属性的设置都用这个标记代替
		 */
		LAYOUT,
		/**
		 * 页眉级别属性标记
		 */
		REGION_BEFORE,
		/**
		 * 页脚级别属性标记
		 */
		REGION_AFTER,
		/**
		 * 左区域属性标记
		 */
		REGION_START,
		/**
		 * 右区域属性标记
		 */
		REGION_END,
		/**
		 * PageSequence级别标记
		 */
		PAGE_SEQUENCE,

		/**
		 * 整个document的属性标记
		 */
		DOCUMENT,

		// /===============表格部分属性分类================////
		/**
		 * 表格级别标记
		 */
		TABLE,

		/**
		 * 表和表头
		 */
		TABLE_AND_CAPTION,

		TABLE_COLUMN,

		TABLE_CAPTION,

		TABLE_HEADER,

		TABLE_FOOTER,

		TABLE_BODY,

		TABLE_ROW,

		TABLE_CELL,
		// /===============表格部分属性分类================////

		/**
		 * 图片级别标记
		 */
		GRAPH,

		/**
		 * svg图形
		 */
		SVG_GRAPHIC,

		/**
		 * block container 级别标记
		 */
		BLOCK_CONTAINER,

		/**
		 * list 级别标记
		 */
		LIST,

		/**
		 * 索引级别标记
		 */
		INDEX,
		/**
		 * 格式化数字
		 */
		NUMBERTEXT, ENCRYPTTEXT,
		/**
		 * 日期时间
		 */
		DATETIME,
		// 可设置组属性对象
		Groupable,
		// 【添加】用于处理统计图 by 李晓光 2009-5-21
		Chart, Edit, Datatreat, WordArtText, CheckBox;
	}

	/**
	 * 默认动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Defalult implements ActionID
	{
		// 添加表尾动作
		ADD_TABLEFOOTER_ACTION,
		// 添加表头动作
		ADD_TABLEHEADER_ACTION,
		// 在当前行之前添加行动作
		ADD_TABLEROW_ABOVE_ACTION,
		// 在当前行之后添加行动作
		ADD_TABLEROW_BELOW_ACTION,
		// 在当前单元格左侧插入单元格
		ADD_TABLECELL_LEFT_ACTION,
		// 在当前单元格右侧插入单元格
		ADD_TABLECELL_RIGHT_ACTION,
		// 预览动作
		BROWSE_ACTION,
		//html模板预览
		HTMLBROWSE_ACTION,
		// 联系我们动作
		CONTACT_ACTION,
		// 复制动作
		COPY_ACTION,
		// 粘贴操作
		PASTE_ACTION,
		// 粘贴操作
		PASTE_WITHOUTBINDNODE_ACTION,
		// 紧复制文本动作
		PASTE_TEXT_ACTION,
		// 剪切事件
		CUT_ACTION,
		// 删除表格
		DELETE_TABLE_ACTION,
		// 删除单元格
		DELETE_TABLECELL_ACTION,
		// 删除表尾
		DELETE_TABLEFOOTER_ACTION,
		// 删除表头
		DELETE_TABLEHEADER_ACTION,
		// 删除表行
		DELETE_TABLEROW_ACTION,
		// 编辑数据设置操作
		EDIT_STRUCTURE_ACTION,
		// 导出当前文档模板操作
		EXPLORT_XSLTFILE_ACTION,
		// 导出当前文档标准模板操作
		EXPLORT_STANDARD_XSLTFILE_ACTION,
		// 导出当前章节模板操作
		EXPLORT_PSXSLTFILE_ACTION,
		// 导出当前章节标准模板操作
		EXPLORT_PS_STANDARD_XSLTFILE_ACTION,
		// 导出html模板操作
		EXPLORT_HTML_XSLTFILE_ACTION,
		// 导出模板用到的XPATH文件操作
		EXPLORT_XPTAH_FILE_ACTION,
		// 插入绝对定位文本框操作
		INSERT_BLOCKCONTAINER_ACTION,
		// 插入相对定位文本框操作
		INSERT_RELATIVEBLOCKCONTAINER_ACTION,
		// 插入日期时间对象
		INSERT_DATETIME_ACTION,
		// 插入图片
		INSERT_IMAGE_ACTION,
		// 插入数字
		INSERT_NUMBERTEXT_ACTION,
		//插入序号对象
		INSERT_POSITIONINLINE_1_ACTION,
		INSERT_POSITIONINLINE_a_ACTION,
		INSERT_POSITIONINLINE_A_ACTION,
		INSERT_POSITIONINLINE_i_ACTION,
		INSERT_POSITIONINLINE_I_ACTION,
		// 插入页码
		INSERT_PAGENUMBER_ACTION,
		// 插入章节
		INSERT_PAGE_SEQUENCE,
		// 插入子模板
		INSERT_ZIMOBAN,
		// 插入表格
		INSERT_TABLE_ACTION,
		// 插入当前章节总页码 add zhongyajun
		INSERT_PSTOTALPAGENUMBER_ACTION,
		// add zhongyajun end
		// 插入总页码
		INSERT_TOTALPAGENUMBER_ACTION,
		// 插入目录
		INSERT_TABLECONTENTS_ACTION,
		// 插入加密文本
		INSERT_ENCRYPTTEXT_ACTION,
		// 更新目录
		UPDATE_TABLECONTENTS_ACTION,
		// 导入动态数据结构
		LOAD_STRUCTURE_ACTION,
		// 导入其他格式（CSV，TXT数据库表等）的动态数据结构
		LOAD_SQL_STRUCTURE_ACTION,
		// 合并单元格
		MERGE_TABLECELL_ACTION,
		// 新建文档
		NEW_DOCUMENT_ACTION,
		// 模板管理
		MODEL_MANEGEMENT_ACTION,
		// 从模板新建
		MODEL_OPEN_ACTION,
		// 模板编辑
		MODEL_EDIT_ACTION,
		// 打开文档操作
		OPEN_FILE_ACTION,
		// 生成注册信息
		GENERATE_REGISTRATION_INFORMATION,
		// 导入注册文件
		LOAD_REGISTRATION_DOCUMENT,
		

		// 重做操作
		REDO_ACTION,
		// 清除数据结构操作
		REMOVE_STRUCTURE_ACTION,
		// 另存为
		SAVEAS_WSD_ACTION, SAVEAS_WSDT_ACTION, SAVEAS_WSDX_ACTION, SAVEAS_WSDM_ACTION,
		// 文档保存操作
		SAVE_FILE_ACTION,
		// 拆分单元格操作
		SPLIT_TABLECELL_ACTION,
		// 撤销操作
		UNDO_ACTION,
		// 清除段落的显示条件设置
		REMOVE_BLOCKCONDITION_ACTION,
		// 清除inline对象上的显示条件属性
		REMOVE_INLINECONDITION_ACTION,
		// 清除当前选中对象上的显示条件属性
		REMOVE_OBJECTCONDTION_ACTION,
		// 清除当前选中章节上的显示条件属性
		REMOVE_PAGESEQUENCECONDTION_ACTION,
		// 设置显示段落条件属性
		SET_BLOCKCONDITION_ACTION,
		// 设置inline对象上的显示条件属性
		SET_INLINECONDITION_ACTION,
		// 设置当前选中对象上的显示条件属性
		SET_OBJECTCONDTION_ACTION,
		// 设置当前选中章节上的显示条件属性
		SET_PAGESEQUENCECONDTION_ACTION,

		// 清除组属性中条件属性
		REMOVE_GROUPLOGICALEXPREESION_ACTION, REMOVE_GROUPSORTS_ACTION,
		// 设置组属性过滤条件
		SET_GROUPLOGICALEXPREESION_ACTION,
		// 设置组属性最大重复次数
		SET_GROUPMAXNUMBER_ACTION,
		// 设置组属性的编辑属性
		SET_GROUPEDIT_ACTION,
		// 设置组属性的重复节点属性
		SET_GROUPNODE_ACTION,
		// 设置组属性的排序属性
		SET_GROUPSORTS_ACTION,
		// 插入直线操作
		INSERT_LINE_ACTION,
		// 插入矩形操作
		INSERT_RECTANGLE_ACTION,
		// 插入椭圆操作
		INSERT_ELLIPSE_ACTION,
		// 插入画布操作
		INSERT_CANVAS_ACTION,
		// 插入统计图事件
		INSERT_CHART_ACTION,
		// 插入艺术字
		INSERT_WORDARTTEXT_ACTION,
		// // 设置组属性
		// SET_GROUP_PROPERTY,
		// // 清除组属性
		// REMOVE_GROUP_PROPERTY,
		// 当前图文
		SET_CURRENT_INLINE,
		// 当前段落
		SET_CURRENT_BLOCK,
		// 当前章节
		SET_CURRENT_PAGE_SEQUENCE,
		// 当前文档
		SET_CURRENT_WISEDOCEMENT,
		// 当前组合
		SET_CURRENT_OBJECT,
		// 当前对象
		SET_CURRENT_GROUP,
		// 当前图文
		REMOVE_CURRENT_INLINE,
		// 当前段落
		REMOVE_CURRENT_BLOCK,
		// 当前章节
		REMOVE_CURRENT_PAGE_SEQUENCE,
		// 当前文档
		REMOVE_CURRENT_WISEDOCEMENT,
		// 当前对象
		REMOVE_CURRENT_OBJECT,
		// 当前组合
		REMOVE_CURRENT_GROUP,
		// 设置为一个重复组操作
		GROUP_ACTION,
		// 撤销组合操作
		UNGROUP_ACTION,
		// 当前章节
		SET_CURRENT_PAGE_SEQUENCE_DYNAMIC_STYLE,
		// 当前文档
		SET_CURRENT_WISEDOCEMENT_DYNAMIC_STYLE,
		// 当前对象
		SET_CURRENT_OBJECT_DYNAMIC_STYLE,
		// 当前图文
		REMOVE_CURRENT_INLINE_DYNAMIC_STYLE,
		// 当前段落
		REMOVE_CURRENT_BLOCK_DYNAMIC_STYLE,
		// 当前章节
		REMOVE_CURRENT_PAGE_SEQUENCE_DYNAMIC_STYLE,
		// 当前文档
		REMOVE_CURRENT_WISEDOCEMENT_DYNAMIC_STYLE,
		// 当前对象
		REMOVE_CURRENT_OBJECT_DYNAMIC_STYLE, INSERT_SPECIALCHARACTER_ACTION, INSERT_BLOCKBEFORE_ACTION, INSERT_BLOCKAFTER_ACTION, INSERT_CHECKBOX_ACTION;

		@Override
		public ActionType getType()
		{
			// TODO Auto-generated method stub
			return ActionType.UNDEFINED;
		}

	}

	/**
	 * 字体动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Font implements ActionID
	{
		/** 清空字体样式 */
		CLEAN_ACTION,
		/** 更改字体动作 */
		FAMILY_ACTION,
		/** 更改字号动作 */
		SIZE_ACTION,
		/** 粗体、斜体、粗斜体整体动作 */
		STYLE_ACTION,
		/** 字体颜色动作 */
		COLOR_ACTION,
		/** 下划线动作 */
		UNDERLINE_ACTION,
		/** 上划线动作 */
		OVERLINE_ACTION,
		/** 删除线动作 */
		LINE_THROUGH_ACTION,
		/** 高亮动作 */
		HIGH_LIGHTING_ACTION,
		/** 背景颜色动作 */
		BACKGROUND_COLOR_ACTION,
		/** 背景颜色层次动作 */
		BACKGROUND_COLOR_LAYER_ACTION,
		/** 字符相对位置动作，（上标、下标） */
		POSITION_ACTION,
		/** 根据用户输入确定字符相对位置 */
		POSITION_INPUT_ACTION,
		/** 字间距动作 */
		SPACE_ACTION,
		/** 根据用户输入确定字间距 */
		SPACE_INPUT_ACTION,
		/** 文字拉伸动作 */
		STRETCH_ACTION,
		/** 词间距动作 */
		WORD_SPACE_ACTION,
		/** 根据用户输入设置词间距 */
		WORD_SPACE_INPUT_ACTION,
		/** 文字边框设置动作 */
		BORDER_SETTING_ACTION,
		/** 文字边框宽度设置动作 */
		BORDER_WIDTH_ACTION,
		/** 文字边框颜色设置动作 */
		BORDER_COLOR_ACTION,
		/** 文字边框样式设置动作 */
		BORDER_STYLE_ACTION,
		/** 配合Ribbon的粗体动作 */
		BOLD_ACTION,
		/** 配合Ribbon的斜体动作 */
		ITALIC_ACTION,
		/** 配合Ribbon的上标动作 */
		SUPER_ACTION,
		/** 配合Ribbon的下标动作 */
		SUB_ACTION,
		/** 设置颜色层次的动作 */
		COLOR_LAYER_ACTION,

		BEFORE_BORDER_ACTION,

		AFTER_BORDER_ACTION,

		START_BORDER_ACTION,

		END_BORDER_ACTION,

		ALL_BORDER_ACTION,

		NONE_BORDER_ACTION, // 当前图文
		SET_CURRENT_INLINE_DYNAMIC_STYLE;

		public ActionType getType()
		{
			return ActionType.INLINE;
		}

	}

	/**
	 * 段落动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Paragraph implements ActionID
	{

		/** 右缩进动作 */
		START_INDENT,
		/** 左缩进动作 */
		END_INDENT,
		/** 段落内文本对齐方式动作 */
		/** 尾行对齐方式动作 */
		DISPLAY_ALLIGN, SPECIAL_INDENT, SPECIAL_INDENT_INPUT_ACTION, BACKGROUND_COLOR_ACTION, BACKGROUND_COLOR_LAYER_ACTION, LINE_HEIGHT_ACTION, LINE_SPACE_ACTION, LINE_SPACE_INPUT_ACTION, LINE_SPACE_BEFORE_ACTION, LINE_SPACE_AFTER_ACTION, BORDER_SETTING_ACTION, BORDER_WIDTH_ACTION, BORDER_COLOR_ACTION, BORDER_STYLE_ACTION, LINE_HEIGHT_STRATEGY_ACTION,
		/** Ribbon的默认情况下的对齐动作 */
		TEXT_ALIGN_START_ACTION,
		/** Ribbon的左对齐动作 */
		TEXT_ALIGN_LEFT_ACTION,
		/** Ribbon的右对齐动作 */
		TEXT_ALIGN_END_ACTION,
		/** Ribbon的居中 */
		TEXT_ALIGN_CENTER_ACTION,
		/** Ribbon的两端对齐 */
		TEXT_ALIGN_JUSTIFY_ACTION,
		/** Ribbon的无线框动作 */
		NONE_BORDER_ACTION,
		/** 下边框设置动作 */
		AFTER_BORDER_ACTION,
		/** 上边框设置动作 */
		BEFORE_BORDER_ACTION,
		/** 左边框 */
		START_BORDER_ACTION,
		/** 右边框 */
		END_BORDER_ACTION,
		/** 全部设置成默认边框 */
		ALL_BORDER_ACTION,
		/** 段前分页 */
		BREAK_BEFORE_ACTION,
		/** 断后分页 */
		BREAK_AFTER_ACTION, KEEP_TOGETHER_ACTION, KEEP_WITH_NEXT_ACTION, KEEP_WITH_PREVIOUS_ACTION
		// 1倍行间距
		, LINEHEIGHT_100_ACTION
		// 1.15倍行间距
		, LINEHEIGHT_115_ACTION
		// 1.5倍行间距
		, LINEHEIGHT_150_ACTION
		// 2倍行间距
		, LINEHEIGHT_200_ACTION
		// 2.5倍行间距
		, LINEHEIGHT_250_ACTION
		// 3倍行间距
		, LINEHEIGHT_300_ACTION, // 当前段落
		SET_CURRENT_BLOCK_DYNAMIC_STYLE,
		// 目录级别的两个动作
		LEVEL_ACTION, LEVEL_CANCEL_ACTION;

		public ActionType getType()
		{
			return ActionType.BLOCK;
		}

	}

	/**
	 * 页布局动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Page implements ActionID
	{
		// 页面横纵方向
		ORIENTATION_ACTION, TEXT_DIRECTION_ACTION, PAPER_SIZE_ACTION, PAPER_SIZE_INPUT_ACTION, BODY_MARGIN_ACTION, SHOW_HEADER_ACTION, COLUMN_COUNT_ACTION, COLUMN_GAPE_ACTION, BODY_TEXT_DIRECTION_ACTION, BODY_DISPLAY_ALIGN, PAGE_SIZE_ACTION, MARGIN_ACTION, BODY_BACKGROUND_COLOR_ACTION, BODY_BACKGROUND_IMAGE_ACTION,
		// 删除背景图片设置属性
		REMOVE_BODYBGIMAGEACTION, BODY_BACKGROUND_IMAGE_REPEAT_ACTION, BODY_BACKGROUND_POSITION_HORIZONTAL, BODY_BACKGROUND_POSITION_VERTICAL, ADD_SIMPLE_MASTER_LAYOUT_ACTION, EDIT_SIMPLE_MASTER_LAYOUT_ACTION, ADD_SEQUENCE_MASTER_LAYOUT_ACTION, EDIT_SEQUENCE_MASTER_LAYOUT_ACTION, ADD_FIRST_LAYOUT_ACTION,

		ADD_LAST_LAYOUT_ACTION, ADD_ODD_AND_EVEN_LAYOUT_ACTION, ADD_ALL_PAGE_SEQUENCE_ACTION,

		SET_SIMPLE_PAGE_SEQUENCE_MASTER_ACTION,

		SET_COMPLEX_PAGE_SEQUENCE_MASTER_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	/**
	 * 页眉属性ID集合
	 * 
	 */
	public enum RegionBefore implements ActionID
	{
		INSTANCE, EXTENT_ACTION, REFERENCE_ORIENTATION_ACTION, WRITING_MODE_ACTION,

		OVERFLOW_ACTION, DISPLAY_ALIGN_ACTION, BACKGROUND_COLOR_ACTION,

		BACKGROUND_IMAGE_ACTION, BACKGROUND_IMAGE_REMOVE_ACTION,

		PRECEDENCE_ACTION, DELETE_CONTENT_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	/**
	 * 页脚属性ID
	 * 
	 */
	public enum RegionAfter implements ActionID
	{
		INSTANCE, EXTENT_ACTION, REFERENCE_ORIENTATION_ACTION, WRITING_MODE_ACTION,

		OVERFLOW_ACTION, DISPLAY_ALIGN_ACTION, BACKGROUND_COLOR_ACTION,

		BACKGROUND_IMAGE_ACTION, BACKGROUND_IMAGE_REMOVE_ACTION,

		PRECEDENCE_ACTION, DELETE_CONTENT_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	public enum RegionStart implements ActionID
	{
		INSTANCE, EXTENT_ACTION, REFERENCE_ORIENTATION_ACTION, WRITING_MODE_ACTION,

		OVERFLOW_ACTION, DISPLAY_ALIGN_ACTION, BACKGROUND_COLOR_ACTION,

		BACKGROUND_IMAGE_ACTION, BACKGROUND_IMAGE_REMOVE_ACTION,

		PRECEDENCE_ACTION, DELETE_CONTENT_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	public enum RegionEnd implements ActionID
	{
		INSTANCE, EXTENT_ACTION, REFERENCE_ORIENTATION_ACTION, WRITING_MODE_ACTION,

		OVERFLOW_ACTION, DISPLAY_ALIGN_ACTION, BACKGROUND_COLOR_ACTION,

		BACKGROUND_IMAGE_ACTION, BACKGROUND_IMAGE_REMOVE_ACTION,

		PRECEDENCE_ACTION, DELETE_CONTENT_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	/**
	 * 页序列动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum PageSequence implements ActionID
	{
		// 设置起始页码属性
		SET_INITIAL_PAGE_NUMBER_ACTION,
		// 设置页数属性
		SET_FORCEPAGECOUNT_ACTION, // 设置页码样式属性
		SET_FORMAT_ACTION,
		DELETE_ACTION;

		public ActionType getType()
		{
			return ActionType.LAYOUT;
		}
	}

	/**
	 * 针对整个文档的动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum WiseDocument implements ActionID
	{

		EDITABLE, // 设置连续空格是否保留属性
		DBTYPE,
		SET_WHITESPACECOLLAPSE_ACTION,
		// 设置空格处理方式属性
		SET_WHITESPACETREATMENT_ACTION,
		// 设置换行符处理方式属性
		SET_LINEFEEDTREATMENT_ACTION;

		public ActionType getType()
		{
			return ActionType.DOCUMENT;
		}
	}

	/**
	 * 目录属性标记
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Index implements ActionID
	{
		INSTANCE;

		public ActionType getType()
		{
			return ActionType.INDEX;
		}
	}

	/**
	 * 表格动作id集合
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Table implements ActionID
	{
		WIDTH_ACTION, WIDTH_INPUT_ACTION, END_POSITION_ACTION, START_POSITION_ACTION, BACKGROUND_COLOR_ACTION, BACKGROUND_IMAGE_ACTION, REMOVE_BACKGROUNDIMAGE, DISPLAY_ALIGN_ACTION
		/** Ribbon的无线框动作 */
		, NONE_BORDER_ACTION,
		/** 下边框设置动作 */
		AFTER_BORDER_ACTION,
		/** 上边框设置动作 */
		BEFORE_BORDER_ACTION,
		/** 左边框 */
		START_BORDER_ACTION,
		/** 右边框 */
		END_BORDER_ACTION,
		/** 全部设置成默认边框 */
		ALL_BORDER_ACTION;

		public ActionType getType()
		{
			return ActionType.TABLE;
		}

	}

	public enum TableAndCaption implements ActionID
	{
		TYPE;

		public ActionType getType()
		{
			return ActionType.TABLE_AND_CAPTION;
		}
	}

	public enum TableHeader implements ActionID
	{
		TYPE;

		public ActionType getType()
		{
			return ActionType.TABLE_HEADER;
		}
	}

	public enum TableFooter implements ActionID
	{
		TYPE;

		public ActionType getType()
		{
			return ActionType.TABLE_FOOTER;
		}
	}

	public enum TableBody implements ActionID
	{
		TYPE;

		public ActionType getType()
		{
			return ActionType.TABLE_BODY;
		}
	}

	public enum TableRow implements ActionID
	{
		TYPE, HEIGHT_ACTION;

		public ActionType getType()
		{
			return ActionType.TABLE_ROW;
		}
	}

	public enum TableCell implements ActionID
	{
		TYPE, WIDTH_ACTION, BACKGROUND_COLOR_ACTION, BACKGROUND_IMAGE_ACTION, REMOVE_BACKGROUNDIMAGE, DISPLAY_ALIGN_ACTION, SIZE_TYPE
		/** Ribbon的无线框动作 */
		, NONE_BORDER_ACTION,
		/** 下边框设置动作 */
		AFTER_BORDER_ACTION,
		/** 上边框设置动作 */
		BEFORE_BORDER_ACTION,
		/** 左边框 */
		START_BORDER_ACTION,
		/** 右边框 */
		END_BORDER_ACTION,
		/** 左缩进设置动作 */
		LEFT_PADDING_ACTION,
		/** 右缩进设置动作 */
		RIGHT_PADDING_ACTION,
		/** 上缩进设置动作 */
		TOP_PADDING_ACTION,
		/** 下缩进设置动作 */
		BOTTOM_PADDING_ACTION,
		/** 全部设置成默认边框 */
		ALL_BORDER_ACTION;

		public ActionType getType()
		{
			return ActionType.TABLE_CELL;
		}

	}

	/**
	 * 条形码动作ID常量
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum Barcode implements ActionID
	{
		TYPE_39_ACTION, TYPE_39_DEFAULT_ACTION, TYPE_128_DEFAULT_ACTION, TYPE_25_DEFAULT_ACTION, TYPE_UPCE_DEFAULT_ACTION, TYPE_EAN8_DEFAULT_ACTION, TYPE_UPCA_DEFAULT_ACTION, TYPE_EAN128_DEFAULT_ACTION, TYPE_EAN13_DEFAULT_ACTION, INPUT_CONTENT_ACTION, ADD_CHECKSUM_ACTION, MAKE_UCC_ACTION, QUIET_HORIZONTAL_ACTION, QUIET_VERTICAL_ACTION, ORIENTATION_ACTION, HEIGHT_ACTION, WIDTH_ACTION, FONT_FAMILY_ACTION, FONT_SIZE_ACTION, SUBSET_ACTION, STRING_ACTION, PRINT_TEXT_ACTION, TEXT_CHAR_SPACE_ACTION, TEXT_BLOCK_ACTION, CLEAN_STYLE_ACTION, WIDE_TO_NARROW_ACTION, TYPE_PDF417_DEFAULT_ACTION, EC_LEVEL_ACTION, COLUMNS_ACTION, MIN_COLUMNS_ACTION, MAX_COLUMNS_ACTION, MIN_ROWS_ACTION, MAX_ROWS_ACTION;

		public ActionType getType()
		{
			return ActionType.GRAPH;
		}
	}

	public enum ExternalGraphic implements ActionID
	{
		SET_ALPHA_ACTION, RESET_ACTION, SET_CONTENTWIDTH_ACTION, SET_CONTENTHEIGHT_ACTION, SET_DATATYPE_ACTION, SET_LAYER_ACTION, SET_SCALING_ACTION, SET_SIZETYPE_ACTION, SET_SRCURL_ACTION
		/** Ribbon的无线框动作 */
		, NONE_BORDER_ACTION,
		/** 下边框设置动作 */
		AFTER_BORDER_ACTION,
		/** 上边框设置动作 */
		BEFORE_BORDER_ACTION,
		/** 左边框 */
		START_BORDER_ACTION,
		/** 右边框 */
		END_BORDER_ACTION,
		/** 全部设置成默认边框 */
		ALL_BORDER_ACTION,
		/** 是否可编辑 */
		GRAPHIC_ISEDITABLE_ACTION,
		/** 编辑权限 */
		EDIT_AUTHORITY_ACTION
		;

		
		public ActionType getType()
		{
			return ActionType.GRAPH;
		}
	}
	public enum QianZhang implements ActionID
	{
		INSERT_ACTION,
		CHANGE_ACTION;

		public ActionType getType()
		{
			return ActionType.GRAPH;
		}
	}
	/**
	 * 有关svg图形的动作id
	 */
	public enum SvgGraphic implements ActionID
	{
		Instance, CANVAS_HEIGHT, CANVAS_WIDTH, X_ACTION, Y_ACTION, HEIGHT_ACTION, WIDTH_ACTION, ROTATE_ACTION, LAYER_ACTION, OPACITY_ACTION, FILL_COLOR_ACTION, FILL_PICTURE_ACTION, REMOVE_PICTURE_ACTION, FILL_OPACITY_ACTION,

		TEXT_CONTENT_ACTION, FONT_FAMILY_ACTION, FONT_SIZE_ACTION, FONT_COLOR_ACTION, FONT_CLEAN_ACTION, FONT_WEIGHT_ACTION, FONT_ITALIC_ACTION, FONT_UNDERLINE_ACTION, FONT_LINE_THROUGH_ACTION,

		HORIZONTAL_CENTER, LEFT_ALIGN, RIGHT_ALIGN, TOP_ALIGN, VERTICAL_CENTER, BOTTOM_ALIGN,

		LINE_TYPE_ACTION, LINE_WIDTH_ACTION, LINE_COLOR_ACTION, EQUAL_HEIGHT, EQUAL_WIDTH, STROKE_LINE_JOIN,

		ARROW_START_STYLE, ARROW_END_STYLE,

		RECTANGLE_RX, RECTANGLE_RY,

		ORIENTATION_ACTION;

		public ActionType getType()
		{
			return ActionType.SVG_GRAPHIC;
		}
	}

	public enum BlockContainer implements ActionID
	{
		TOP_POSITION_ACTION, LEFT_POSITION_ACTION, END_POSITION_ACTION, START_POSITION_ACTION,
		BPD_ACTION, IPD_ACTION, AUTO_BPD_ACTION, AUTO_IPD_ACTION, OVERFLOW_VISIBLE_ACTION,
		OVERFLOW_HIDDEN_ACTION, OVERFLOW_AUTOFONTSIZE_ACTION, OVERFLOW_OTHER_ACTION,
		// 内容决定大小
		SIZEBYCONTENT_ACTION, BACKGROUND_COLOR_ACTION, BACKGROUND_IMAGE_ACTION,
		REMOVE_BACKGROUNDIMAGE, DISPLAY_ALIGN_ACTION
		/** Ribbon的无线框动作 */
		, NONE_BORDER_ACTION, CLEAR_OVERFLOW_OTHER_ACTION,
		/** 下边框设置动作 */
		AFTER_BORDER_ACTION,
		/** 上边框设置动作 */
		BEFORE_BORDER_ACTION,
		/** 左边框 */
		START_BORDER_ACTION,
		/** 右边框 */
		END_BORDER_ACTION,
		/** 全部设置成默认边框 */
		ALL_BORDER_ACTION,

		PADDING_TOP,

		PADDING_BOTTOM,

		PADDING_LEFT,

		PADDING_RIGHT;

		public ActionType getType() {
			return ActionType.BLOCK_CONTAINER;
		}

	}

	/* 【添加：START】 by 李晓光 2009-5-19 */
	public enum WisedocChart implements ActionID
	{
		/* 类型 */
		TYPE_ACTION,
		/* 高 */
		BPD_ACTION,
		/* 宽 */
		IPD_ACTION,
		/* 内部边距 */
		MARGIN_ACTION,
		/* 标题文本 */
		TITLE_ACTION,
		/* 系列数 */
		SERIES_COUNT_ACTION,
		/* 值个数 */
		VALUE_COUNT_ACTION,
		/* 层 */
		LAYER_ACTION,
		/* 背景透明度 */
		BACK_ALPHA_ACTION,
		/* 前景透明度 */
		FRONT_ALPHA_ACTION,
		/* 背景色 */
		BACKGROUND_COLOR_ACTION,
		/* 设置背景图 */
		FILL_PICTURE_ACTION,
		/* 删除背景图 */
		REMOVE_PICTURE_ACTION,

		/* 零基线 */
		SHOW_BASE_LINE_ACTION,
		/* 值坐标轴 */
		SHOW_AXIS_Y_ACTION,
		/* 域坐标轴 */
		SHOW_AXIS_X_ACTION,
		/* 显示值 */
		SHOW_VALUE_ACTION,
		/* 采用3D效果显示 */
		SHOW_3D_ACTION,
		/* 3D深度 */
		DEPTH3D_ACTION,
		/* 值标签 */
		VALUE_TEXT_ACTION,
		/* 域标签 */
		SERIES_TEXT_ACTION,
		/* 最小刻度值 */
		MIN_ADUATE_ACTION,
		/* 最大刻度值 */
		MAX_ADUATE_ACTION,
		/* 刻度跨度 */
		STEP_ADUATE_ACTION,
		/* 采用随机刻度、 */
		AUTO_ADUATE_ACTION,
		/* 值刻度-精度 */
		ADUATE_PRECISION_ACTION,
		/* 标签相对柱状图顶端的偏移量 */
		OFFSET_ACTION,
		/* 指示标签位置【上、下、左、右、左上、左下、右上、右下】 */
		INDICATOR_ACTION,
		/* 饼状图起始角度 */
		START_DEGREE_ACTION,
		/* 系列标签角度 */
		SERIES_LABEL_DEGREE_ACTION,
		/* 饼状图绘制方向【顺时针、逆时针】 */
		PIE_ORIATION_ACTION,
		/* 是否显示饼状图标签 */
		PIE_SHOW_LABEL_ACTION,
		/* 是否显示饼状图百分比值 */
		PIE_SHOW_PERCENT_ACTION,
		/* 是否显示饼状图实际值 */
		PIE_SHOW_FACT_VALUE_ACTION,
		/* 是否显示零值 */
		SHOW_ZERO_VALUE_ACTION,
		/* 是否显Null零值 */
		SHOW_NULL_VALUE_ACTION,
		/* 统计图方向 */
		CHART_ORIENTATION,

		AFTER_BORDER_ACTION, BEFORE_BORDER_ACTION, START_BORDER_ACTION, END_BORDER_ACTION, NONE_BORDER_ACTION, ALL_BORDER_ACTION,

		FAMILY_ACTION, ;

		public ActionType getType()
		{
			return ActionType.Chart;
		}
	}

	/* 【添加：END】 by 李晓光 2009-5-19 */
	public enum NumberText implements ActionID
	{
		THOUSAND_SET_ACTION, BAIFENBI_SET_ACTION, THOUSAND_SEPARATOR_SET_ACTION, CHINESE_NUMBER_SET_ACTION, SET_ISCHINESE_ACTION;

		public ActionType getType()
		{
			return ActionType.NUMBERTEXT;
		}

	}

	/* 【添加：END】 by 李晓光 2009-5-19 */
	public enum EncryptText implements ActionID
	{
		SET_ENCRYPT_ACTION;

		public ActionType getType()
		{
			return ActionType.ENCRYPTTEXT;
		}

	}

	public enum DateTime implements ActionID
	{
		//
		DATE_INPUT_SET_ACTION,
		//
		DATE_OUTPUT_SET_ACTION,
		//
		TIME_INPUT_SET_ACTION,
		//
		TIME_OUTPUT_SET_ACTION,
		//
		INPUT_SEPEAATE_SET_ACTION,
		//
		OUTPUT_SEPEAATE_SET_ACTION;

		public ActionType getType()
		{
			return ActionType.DATETIME;
		}
	}

	/**
	 * 
	 * 类功能描述：编辑相关Action
	 * 
	 * 作者：zhangqiang 创建日期：2009-7-9
	 */
	public enum Edit implements ActionID
	{
		//
		EDIT_TYPE_ACTION,

		EDIT_AUTHORITY_ACTION,

		EDIT_ISRELOAD_ACTION,

		EDIT_APPEARANCE_ACTION,

		EDIT_WIDTH_ACTION,

		EDIT_HEIGHT_ACTION,

		EDIT_HINT_ACTION,

		EDIT_INPUTTYPE_ACTION,

		EDIT_INPUTSIZE_ACTION,

		EDIT_INPUTMULTILINE_ACTION,

		EDIT_INPUTWRAP_ACTION,

		EDIT_INPUTFILTER_ACTION,

		EDIT_INPUTFILTERMSG_ACTION,
		EDIT_INPUTDATASOURCE_ACTION,
		EDIT_DEFAULT_VALUE_ACTION,

		EDIT_DATETYPE_ACTION,

		EDIT_ONBLURVALIDATION_ACTION,

		EDIT_ONEDITVALIDATION_ACTION,

		EDIT_ONRESULTVALIDATION_ACTION,

		REMOVE_ONBLURVALIDATION_ACTION,

		REMOVE_ONEDITVALIDATION_ACTION,

		REMOVE_ONRESULTVALIDATION_ACTION,

		EDIT_SELECTTYPE_ACTION,

		EDIT_SELECTLINES_ACTION,

		EDIT_SELECTEDITABLE_ACTION,

		EDIT_SELECTSHOWLIST_ACTION,

		EDIT_SELECTMULTIPLE_ACTION,

		EDIT_SELECTINFO_ACTION, EDIT_SELECTNEXT_ACTION, REMOVE_SELECTNEXT_ACTION, EDIT_SELECTNEXTNAME_ACTION,EDIT_POPUPBROWSERINFO_ACTION,

		EDIT_CONNWITH_ACTION, REMOVE_CONNWITH_ACTION;

		public ActionType getType()
		{
			return ActionType.Edit;
		}
	}

	public enum Datatreat implements ActionID
	{
		//
		SET_DATATREAT_TRANSFORM_TABLE,

		SET_DATATREAT_XPATH_POSITION,

		REMOVE_DATATREAT_TRANSFORM_TABLE,

		REMOVE_DATATREAT_XPATH_POSITION,

		SET_SIMPLE_DATA_TRANSFORM_TABLE,

		REMOVE_SIMPLE_DATA_TRANSFORM_TABLE,

		SET_BUTTONS,

		REMOVE_BUTTONS;

		public ActionType getType()
		{
			return ActionType.Datatreat;
		}
	}

	public enum WordArtText implements ActionID
	{
		//
		SET_TEXTCONTENT_ACTION,

		SET_LETTERSPACE_ACTION,

		SET_ALIGNMENT_ACTION,

		SET_PATHTYPE_ACTION,

		SET_ROTATION_ACTION,

		SET_STARTPOSITION_ACTION,

		SET_PATHVISABLE_ACTION,

		SET_WIDTH_ACTION,

		SET_HEIGHT_ACTION;

		public ActionType getType()
		{
			return ActionType.WordArtText;
		}
	}

	public enum CheckBox implements ActionID
	{
		//
		CHECKBOX_SELECT_ACTION,

		CHECKBOX_UNSELECT_ACTION,

		CHECKBOX_SET_GROUPUI_ACTION,

		CHECKBOX_CREAT_GROUPUI_ACTION,

		CHECKBOX_BOXSTYLE_ACTION,

		CHECKBOX_BOXSTYLE_LAYER_ACTION,

		CHECKBOX_TICKMARK_ACTION, EDIT_CONNWITH_ACTION, REMOVE_CONNWITH_ACTION, CHECKBOX_ISSELECTED_ACTION, CHECKBOX_ISEDITABLE_ACTION, EDIT_AUTHORITY_ACTION, EDIT_ISRELOAD_ACTION;

		public ActionType getType()
		{
			return ActionType.CheckBox;
		}
	}

	static
	{
		/** Default类型的Action ** 开始 **/
		actionMap.put(Defalult.ADD_TABLEFOOTER_ACTION,
				AddTableFooterAction.class);
		actionMap.put(Defalult.ADD_TABLEHEADER_ACTION,
				AddTableHeaderAction.class);
		actionMap.put(Defalult.ADD_TABLEROW_ABOVE_ACTION,
				AddTableRowAboveAction.class);
		actionMap.put(Defalult.ADD_TABLEROW_BELOW_ACTION,
				AddTableRowBelowAction.class);
		actionMap.put(Defalult.ADD_TABLECELL_LEFT_ACTION,
				AddTableCellLeftAction.class);
		actionMap.put(Defalult.ADD_TABLECELL_RIGHT_ACTION,
				AddTableCellRightAction.class);
		actionMap.put(Defalult.BROWSE_ACTION, BrowseAction.class);
		actionMap.put(Defalult.HTMLBROWSE_ACTION, HtmlBrowseAction.class);
		actionMap.put(Defalult.CONTACT_ACTION, ContactAction.class);
		actionMap.put(Defalult.COPY_ACTION, CopyAction.class);
		actionMap.put(Defalult.PASTE_ACTION, PasteAction.class);
		actionMap.put(Defalult.PASTE_WITHOUTBINDNODE_ACTION, PasteWithOutBindNode.class);
		actionMap.put(Defalult.PASTE_TEXT_ACTION, PasteTextAction.class);
		actionMap.put(Defalult.CUT_ACTION, CutAction.class);
		actionMap.put(Defalult.DELETE_TABLE_ACTION, DeleteTableAction.class);
		actionMap.put(Defalult.DELETE_TABLECELL_ACTION,
				DeleteTableCellAction.class);
		actionMap.put(Defalult.DELETE_TABLEFOOTER_ACTION,
				DeleteTableFooterAction.class);
		actionMap.put(Defalult.DELETE_TABLEHEADER_ACTION,
				DeleteTableHeaderAction.class);
		actionMap.put(Defalult.DELETE_TABLEROW_ACTION,
				DeleteTableRowAction.class);
		actionMap
				.put(Defalult.EDIT_STRUCTURE_ACTION, EditStructureAction.class);
		actionMap.put(Defalult.EXPLORT_XSLTFILE_ACTION,
				ExplortXSLTFileAction.class);
		actionMap.put(Defalult.EXPLORT_PSXSLTFILE_ACTION,
				ExplortPSXSLTFileAction.class);
		actionMap.put(Defalult.EXPLORT_STANDARD_XSLTFILE_ACTION,
				ExplortStandardXSLTFileAction.class);
		actionMap.put(Defalult.EXPLORT_PS_STANDARD_XSLTFILE_ACTION,
				ExplortStandardPSXSLTFileAction.class);
		actionMap.put(Defalult.EXPLORT_HTML_XSLTFILE_ACTION,
				ExplortHtmlXSLTFileAction.class);
		actionMap.put(Defalult.EXPLORT_XPTAH_FILE_ACTION,
				ExplortDataPathFileAction.class);
		actionMap.put(Defalult.INSERT_BLOCKCONTAINER_ACTION,
				InSertBlockContainerAction.class);
		actionMap.put(Defalult.INSERT_RELATIVEBLOCKCONTAINER_ACTION,
				InSertRelativeBlockContainerAction.class);
		actionMap.put(Defalult.INSERT_DATETIME_ACTION,
				InsertDateTimeAction.class);
		actionMap.put(Defalult.INSERT_IMAGE_ACTION, InsertImageAction.class);
		actionMap.put(Defalult.INSERT_NUMBERTEXT_ACTION,
				InsertNumberTextAction.class);
		actionMap.put(Defalult.INSERT_POSITIONINLINE_1_ACTION,
				InsertPositionInline1Action.class);
		actionMap.put(Defalult.INSERT_POSITIONINLINE_a_ACTION,
				InsertPositionInlineaAction.class);
		actionMap.put(Defalult.INSERT_POSITIONINLINE_A_ACTION,
				InsertPositionInline_AAction.class);
		actionMap.put(Defalult.INSERT_POSITIONINLINE_i_ACTION,
				InsertPositionInlineiAction.class);
		actionMap.put(Defalult.INSERT_POSITIONINLINE_I_ACTION,
				InsertPositionInline_IAction.class);
		actionMap.put(Defalult.INSERT_PAGENUMBER_ACTION,
				InsertPageNumberAction.class);
		actionMap.put(Defalult.INSERT_PAGE_SEQUENCE,
				InsertPagesequenceAction.class);
		actionMap.put(Defalult.INSERT_ZIMOBAN, InsertZiMobanAction.class);
		actionMap.put(Defalult.INSERT_SPECIALCHARACTER_ACTION,
				InsertSpecialCharactorAction.class);
		actionMap.put(Defalult.INSERT_CHECKBOX_ACTION,
				InsertCheckBoxAction.class);
		actionMap.put(Defalult.INSERT_BLOCKBEFORE_ACTION,
				InsertBlockBeforeAction.class);
		actionMap.put(Defalult.INSERT_BLOCKAFTER_ACTION,
				InsertBlockAfterAction.class);
		actionMap.put(Defalult.INSERT_TABLE_ACTION, InsertTableAction.class);
		actionMap.put(Defalult.INSERT_TOTALPAGENUMBER_ACTION,
				InsertTotalPageNumberAction.class);
		// add zhongyajun
		actionMap.put(Defalult.INSERT_PSTOTALPAGENUMBER_ACTION,
				InsertPSTotalPageNumberAction.class);
		// add zhongyajun end
		actionMap.put(Defalult.INSERT_TABLECONTENTS_ACTION,
				InsertTableContentsAction.class);
		actionMap.put(Defalult.INSERT_ENCRYPTTEXT_ACTION,
				InsertEncryptTextAction.class);
		actionMap.put(Defalult.UPDATE_TABLECONTENTS_ACTION,
				UpdateTableContentsAction.class);
		actionMap
				.put(Defalult.LOAD_STRUCTURE_ACTION, LoadStructureAction.class);
		actionMap.put(Defalult.LOAD_SQL_STRUCTURE_ACTION,
				LoadSQLStructureAction.class);
		actionMap.put(Defalult.MERGE_TABLECELL_ACTION,
				MergeTableCellAction.class);
		actionMap.put(Defalult.NEW_DOCUMENT_ACTION, NewDocumentAction.class);
		actionMap.put(Defalult.MODEL_MANEGEMENT_ACTION,
				ModelManegemnetAction.class);
		actionMap.put(Defalult.MODEL_OPEN_ACTION, OpenModelAction.class);
		actionMap.put(Defalult.MODEL_EDIT_ACTION, EditModelAction.class);
		actionMap.put(Defalult.OPEN_FILE_ACTION, OpenFileAction.class);

		actionMap.put(Defalult.GENERATE_REGISTRATION_INFORMATION,
				GeneratedRegistrationInformationAction.class);
		actionMap.put(Defalult.LOAD_REGISTRATION_DOCUMENT,
				LoadRegistrationDocumentAction.class);
		actionMap.put(Defalult.REDO_ACTION, RedoAction.class);
		actionMap.put(Defalult.REMOVE_STRUCTURE_ACTION,
				ReMoveStructureAction.class);
		actionMap.put(Defalult.SAVEAS_WSD_ACTION, SaveAsWSDFileAction.class);
		actionMap.put(Defalult.SAVEAS_WSDT_ACTION, SaveAsWSDTFileAction.class);

		actionMap.put(Defalult.SAVEAS_WSDX_ACTION, SaveAsWSDXFileAction.class);
		actionMap.put(Defalult.SAVEAS_WSDM_ACTION, SaveAsWSDMFileAction.class);
		
		
		actionMap.put(Defalult.SAVE_FILE_ACTION, SaveFileAction.class);
		actionMap.put(Defalult.SPLIT_TABLECELL_ACTION,
				SplitTableCellAction.class);
		actionMap.put(Defalult.UNDO_ACTION, UndoAction.class);
		actionMap.put(Defalult.REMOVE_BLOCKCONDITION_ACTION,
				RemoveBlockConditionAction.class);
		actionMap.put(Defalult.REMOVE_INLINECONDITION_ACTION,
				RemoveInlineConditionAction.class);
		actionMap.put(Defalult.REMOVE_OBJECTCONDTION_ACTION,
				RemoveObjectCondtionAction.class);
		actionMap.put(Defalult.REMOVE_PAGESEQUENCECONDTION_ACTION,
				RemovePageSequenceConditionAction.class);
		actionMap.put(Defalult.SET_BLOCKCONDITION_ACTION,
				SetBlockConditionAction.class);
		actionMap.put(Defalult.SET_INLINECONDITION_ACTION,
				SetInlineConditionAction.class);
		actionMap.put(Defalult.SET_OBJECTCONDTION_ACTION,
				SetObjectConditionAction.class);
		actionMap.put(Defalult.SET_PAGESEQUENCECONDTION_ACTION,
				SetPageSequenceConditionAction.class);

		actionMap.put(Defalult.INSERT_LINE_ACTION, InsertLineAction.class);
		actionMap.put(Defalult.INSERT_RECTANGLE_ACTION,
				InsertRectangleAction.class);
		actionMap
				.put(Defalult.INSERT_ELLIPSE_ACTION, InsertEllipseAction.class);
		actionMap.put(Defalult.INSERT_CANVAS_ACTION, InsertCanvasAction.class);
		actionMap.put(Defalult.INSERT_WORDARTTEXT_ACTION,
				InsertWordArtTextAction.class);
		actionMap.put(Defalult.INSERT_CHART_ACTION, InsertChartAction.class);

		actionMap.put(Defalult.SET_CURRENT_INLINE, SetInlineGroupAction.class);
		actionMap.put(Defalult.SET_CURRENT_BLOCK, SetBlockGroupAction.class);
		actionMap.put(Defalult.SET_CURRENT_OBJECT,
				SetCurrentObjectGroupAction.class);
		actionMap.put(Defalult.SET_CURRENT_GROUP,
				SetCurrentGroupGroupAction.class);
		actionMap
				.put(
						Defalult.SET_CURRENT_PAGE_SEQUENCE,
						com.wisii.wisedoc.view.ui.actions.group.SetPagesequenceGroupAction.class);
		actionMap.put(Defalult.SET_CURRENT_WISEDOCEMENT,
				SetWisedocumentGroupAction.class);

		actionMap.put(Defalult.REMOVE_CURRENT_INLINE,
				RemoveInlineGroupAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_BLOCK,
				RemoveBlockGroupAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_PAGE_SEQUENCE,
				RemovePageSequenceGroupAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_WISEDOCEMENT,
				RemoveWisedocumentGroupAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_OBJECT,
				RemoveCurrentObjectGroupAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_GROUP,
				RemoveCurrentGroupGroupAction.class);
		actionMap.put(Defalult.GROUP_ACTION, GroupAction.class);
		actionMap.put(Defalult.UNGROUP_ACTION, UnGroupAction.class);

		actionMap.put(Font.SET_CURRENT_INLINE_DYNAMIC_STYLE,
				SetInlineDynamicStyleAction.class);
		actionMap.put(Paragraph.SET_CURRENT_BLOCK_DYNAMIC_STYLE,
				SetBlockDynamicStyleAction.class);
		actionMap.put(Defalult.SET_CURRENT_PAGE_SEQUENCE_DYNAMIC_STYLE,
				SetPagesequenceDynamicStyleAction.class);
		actionMap.put(Defalult.SET_CURRENT_WISEDOCEMENT_DYNAMIC_STYLE,
				SetWisedocumentDynamicStyleAction.class);
		actionMap.put(Defalult.SET_CURRENT_OBJECT_DYNAMIC_STYLE,
				SetObjectDynamicStyleAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_INLINE_DYNAMIC_STYLE,
				RemoveInlineDynamicStyleAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_BLOCK_DYNAMIC_STYLE,
				RemoveBlockDynamicStyleAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_PAGE_SEQUENCE_DYNAMIC_STYLE,
				RemovePagesequenceDynamicStyleAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_WISEDOCEMENT_DYNAMIC_STYLE,
				RemoveWisedocumentDynamicStyleAction.class);
		actionMap.put(Defalult.REMOVE_CURRENT_OBJECT_DYNAMIC_STYLE,
				RemoveObjectDynamicStyleAction.class);

		/** 只更新Default类型的Action ** 结束 **/

		/** 文字动作id所对应的action ** 开始 **/
		actionMap.put(Font.CLEAN_ACTION, FontCleanAction.class);
		actionMap.put(Font.FAMILY_ACTION, FontFamilyAction.class);
		actionMap.put(Font.SIZE_ACTION, FontSizeAction.class);
		actionMap.put(Font.STYLE_ACTION, FontStyleAction.class);
		actionMap.put(Font.COLOR_ACTION, FontColorAction.class);
		actionMap.put(Font.COLOR_LAYER_ACTION, FontColorLayerAction.class);
		actionMap.put(Font.UNDERLINE_ACTION, FontUnderlineAction.class);
		actionMap.put(Font.OVERLINE_ACTION, FontOverlineAction.class);
		actionMap.put(Font.LINE_THROUGH_ACTION, FontLineThroughAction.class);
		actionMap.put(Font.HIGH_LIGHTING_ACTION,
				FontBackgroundColorAction.class);
		actionMap.put(Font.BACKGROUND_COLOR_ACTION,
				FontBackgroundColorAction.class);
		actionMap.put(Font.BACKGROUND_COLOR_LAYER_ACTION,
				FontBackgroundColorLayerAction.class);
		actionMap.put(Font.POSITION_ACTION, FontPositionAction.class);
		actionMap
				.put(Font.POSITION_INPUT_ACTION, FontPositionInputAction.class);
		actionMap.put(Font.SPACE_ACTION, FontSpaceAction.class);
		actionMap.put(Font.SPACE_INPUT_ACTION, FontSpaceInputAction.class);
		actionMap.put(Font.STRETCH_ACTION, FontStretchAction.class);
		actionMap.put(Font.WORD_SPACE_ACTION, FontWordSpaceAction.class);
		actionMap.put(Font.WORD_SPACE_INPUT_ACTION,
				FontWordSpaceInputAction.class);
		actionMap.put(Font.BORDER_SETTING_ACTION,
				ParagraphBorderSettingAction.class);
		actionMap.put(Font.BORDER_WIDTH_ACTION,
				ParagraphBorderWidthAction.class);
		actionMap.put(Font.BORDER_COLOR_ACTION,
				ParagraphBorderColorAction.class);
		actionMap.put(Font.BORDER_STYLE_ACTION,
				ParagraphBorderStyleAction.class);
		actionMap.put(Font.BOLD_ACTION, FontBoldAction.class);
		actionMap.put(Font.ITALIC_ACTION, FontItalicAction.class);
		actionMap.put(Font.SUPER_ACTION, FontSuperAction.class);
		actionMap.put(Font.SUB_ACTION, FontSubAction.class);
		actionMap.put(Font.BEFORE_BORDER_ACTION, BeforeBorderAction.class);
		actionMap.put(Font.AFTER_BORDER_ACTION, AfterBorderAction.class);
		actionMap.put(Font.START_BORDER_ACTION, StartBorderAction.class);
		actionMap.put(Font.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(Font.ALL_BORDER_ACTION, AllBorderAction.class);
		actionMap.put(Font.NONE_BORDER_ACTION, NoneBorderAction.class);
		/** 文字动作id所对应的action ** 结束 **/

		/** 段落动作id所对应的action ** 开始 **/
		actionMap.put(Paragraph.START_INDENT, ParagraphStartIndentAction.class);
		actionMap.put(Paragraph.END_INDENT, ParagraphEndIndentAction.class);
		actionMap.put(Paragraph.DISPLAY_ALLIGN,
				ParagraphDisplayAlignAction.class);
		actionMap.put(Paragraph.SPECIAL_INDENT,
				ParagraphSpecialIndentAction.class);
		actionMap.put(Paragraph.SPECIAL_INDENT_INPUT_ACTION,
				ParagraphSpecialIndentInputAction.class);
		actionMap.put(Paragraph.BACKGROUND_COLOR_ACTION,
				ParagraphBackgroundColorAction.class);
		actionMap.put(Paragraph.BACKGROUND_COLOR_LAYER_ACTION,
				ParagraphBackgroundColorLayerAction.class);
		actionMap.put(Paragraph.LINE_HEIGHT_ACTION,
				ParagraphLineHeightAction.class);
		actionMap.put(Paragraph.LINE_SPACE_ACTION,
				ParagraphLineSpaceAction.class);
		actionMap.put(Paragraph.LINE_SPACE_INPUT_ACTION,
				ParagraphLineSpaceInputAction.class);
		actionMap.put(Paragraph.LINE_SPACE_BEFORE_ACTION,
				ParagraphLineSpaceBeforeAction.class);
		actionMap.put(Paragraph.LINE_SPACE_AFTER_ACTION,
				ParagraphLineSpaceAfterAction.class);
		actionMap.put(Paragraph.BORDER_SETTING_ACTION,
				ParagraphBorderSettingAction.class);
		actionMap.put(Paragraph.BORDER_WIDTH_ACTION,
				ParagraphBorderWidthAction.class);
		actionMap.put(Paragraph.BORDER_COLOR_ACTION,
				ParagraphBorderColorAction.class);
		actionMap.put(Paragraph.BORDER_STYLE_ACTION,
				ParagraphBorderStyleAction.class);
		actionMap.put(Paragraph.LINE_HEIGHT_STRATEGY_ACTION,
				ParagraphLineHeightStrategyAction.class);
		actionMap.put(Paragraph.TEXT_ALIGN_START_ACTION,
				ParagraphTextAlignStartAction.class);
		// actions.put(Paragraph.TEXT_ALIGN_LEFT_ACTION, Paragraphtext.class);
		actionMap.put(Paragraph.TEXT_ALIGN_END_ACTION,
				ParagraphTextAlignEndAction.class);
		actionMap.put(Paragraph.TEXT_ALIGN_CENTER_ACTION,
				ParagraphTextAlignCenterAction.class);
		actionMap.put(Paragraph.TEXT_ALIGN_JUSTIFY_ACTION,
				ParagraphTextAlignJustifyAction.class);
		actionMap.put(Paragraph.NONE_BORDER_ACTION, NoneBorderAction.class);
		actionMap.put(Paragraph.AFTER_BORDER_ACTION, AfterBorderAction.class);
		actionMap.put(Paragraph.BEFORE_BORDER_ACTION, BeforeBorderAction.class);
		actionMap.put(Paragraph.START_BORDER_ACTION, StartBorderAction.class);
		actionMap.put(Paragraph.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(Paragraph.ALL_BORDER_ACTION, AllBorderAction.class);
		actionMap.put(Paragraph.BREAK_BEFORE_ACTION,
				ParagraphBreakBeforeAction.class);
		actionMap.put(Paragraph.BREAK_AFTER_ACTION,
				ParagraphBreakAfterAction.class);
		actionMap.put(Paragraph.KEEP_TOGETHER_ACTION,
				ParagraphKeepTogetherAction.class);
		actionMap.put(Paragraph.KEEP_WITH_NEXT_ACTION,
				ParagraphKeepWithNextAction.class);
		actionMap.put(Paragraph.KEEP_WITH_PREVIOUS_ACTION,
				ParagraphKeepWithPreviousAction.class);
		actionMap.put(Paragraph.LINEHEIGHT_100_ACTION,
				ParagraphLineSpace100Action.class);
		actionMap.put(Paragraph.LINEHEIGHT_115_ACTION,
				ParagraphLineSpace115Action.class);
		actionMap.put(Paragraph.LINEHEIGHT_150_ACTION,
				ParagraphLineSpace150Action.class);
		actionMap.put(Paragraph.LINEHEIGHT_200_ACTION,
				ParagraphLineSpace200Action.class);
		actionMap.put(Paragraph.LINEHEIGHT_250_ACTION,
				ParagraphLineSpace250Action.class);
		actionMap.put(Paragraph.LINEHEIGHT_300_ACTION,
				ParagraphLineSpace300Action.class);
		/** 段落动作id所对应的action ** 结束 **/

		/** 页面动作id所对应的action ** 开始 **/
		actionMap.put(Page.ORIENTATION_ACTION, PageOrientationAction.class);
		actionMap
				.put(Page.TEXT_DIRECTION_ACTION, PageTextDirectionAction.class);
		actionMap.put(Page.PAPER_SIZE_ACTION, PagePaperSizeAction.class);
		actionMap.put(Page.PAPER_SIZE_INPUT_ACTION,
				PagePaperSizeInputAction.class);
		actionMap.put(Page.BODY_MARGIN_ACTION, PageBodyMarginAction.class);
		actionMap.put(Page.SHOW_HEADER_ACTION, PageShowHeaderAction.class);
		actionMap.put(Page.COLUMN_COUNT_ACTION, PageColumnCountAction.class);
		actionMap.put(Page.COLUMN_GAPE_ACTION, PageColumnGapAction.class);
		actionMap
				.put(Page.BODY_DISPLAY_ALIGN, PageBodyDisplayAlignAction.class);
		actionMap.put(Page.MARGIN_ACTION, PageMarginAction.class);
		actionMap.put(Page.BODY_TEXT_DIRECTION_ACTION,
				PageBodyTextDirectionAction.class);
		actionMap.put(Page.BODY_BACKGROUND_COLOR_ACTION,
				PageBodyBackgroundColorAction.class);
		actionMap.put(Page.BODY_BACKGROUND_IMAGE_ACTION,
				PageBodyBackgroundImageAction.class);
		actionMap.put(Page.REMOVE_BODYBGIMAGEACTION,
				RemoveBodyBackgroundImageAction.class);
		actionMap.put(Page.BODY_BACKGROUND_IMAGE_REPEAT_ACTION,
				PageBodyBackgroundImageRepeatAction.class);
		actionMap.put(Page.BODY_BACKGROUND_POSITION_HORIZONTAL,
				PageBodyBackgroundPositionHorizontal.class);
		actionMap.put(Page.BODY_BACKGROUND_POSITION_VERTICAL,
				PageBodyBackgroundPositionVertical.class);
		actionMap.put(Page.ADD_SIMPLE_MASTER_LAYOUT_ACTION,
				PageAddSimpleMasterLayoutAction.class);
		actionMap.put(Page.EDIT_SIMPLE_MASTER_LAYOUT_ACTION,
				PageEditSimpleMasterLayoutAction.class);
		actionMap.put(Page.ADD_SEQUENCE_MASTER_LAYOUT_ACTION,
				PageAddSequenceMasterLayoutAction.class);
		actionMap.put(Page.EDIT_SEQUENCE_MASTER_LAYOUT_ACTION,
				PageEditSequenceMasterLayoutAction.class);

		actionMap.put(Page.ADD_FIRST_LAYOUT_ACTION, AddFirstLayoutAction.class);
		actionMap.put(Page.ADD_LAST_LAYOUT_ACTION, AddLastLayoutAction.class);
		actionMap.put(Page.ADD_ODD_AND_EVEN_LAYOUT_ACTION,
				AddOddAndEvenLayoutAction.class);
		actionMap.put(Page.ADD_ALL_PAGE_SEQUENCE_ACTION,
				AddAllPageSequenceLayoutAction.class);

		actionMap.put(Page.SET_SIMPLE_PAGE_SEQUENCE_MASTER_ACTION,
				SetSimplePageSequenceMasterAction.class);
		actionMap.put(Page.SET_COMPLEX_PAGE_SEQUENCE_MASTER_ACTION,
				SetComplexPageSequenceMasterAction.class);

		/** 页面动作id所对应的action ** 结束 **/

		/** 页眉动作 ************* 开始 *******/
		actionMap.put(RegionBefore.EXTENT_ACTION,
				RegionBeforeExtentAction.class);
		actionMap.put(RegionBefore.DELETE_CONTENT_ACTION,
				RegionBeforeDeleteContentAction.class);
		actionMap.put(RegionBefore.PRECEDENCE_ACTION,
				RegionBeforePrecedenceAction.class);
		actionMap.put(RegionBefore.WRITING_MODE_ACTION,
				RegionBeforeWritingModeAction.class);
		actionMap.put(RegionBefore.DISPLAY_ALIGN_ACTION,
				RegionBeforeDisplayAlignAction.class);
		actionMap.put(RegionBefore.REFERENCE_ORIENTATION_ACTION,
				RegionBeforeReferenceOrientationAction.class);
		actionMap.put(RegionBefore.OVERFLOW_ACTION,
				RegionBeforeOverflowAction.class);
		actionMap.put(RegionBefore.BACKGROUND_COLOR_ACTION,
				RegionBeforeBackgroundColorAction.class);
		actionMap.put(RegionBefore.BACKGROUND_IMAGE_ACTION,
				RegionBeforeBackgroundImageAction.class);
		actionMap.put(RegionBefore.BACKGROUND_IMAGE_REMOVE_ACTION,
				RegionBeforeBackgroundImageRemoveAction.class);
		/** 页眉动作 ************* 结束 *******/

		/** 页脚动作 ************* 开始 *******/
		actionMap.put(RegionAfter.EXTENT_ACTION, RegionAfterExtentAction.class);
		actionMap.put(RegionAfter.PRECEDENCE_ACTION,
				RegionAfterPrecedenceAction.class);
		actionMap.put(RegionAfter.DELETE_CONTENT_ACTION,
				RegionAfterDeleteContentAction.class);
		actionMap.put(RegionAfter.WRITING_MODE_ACTION,
				RegionAfterWritingModeAction.class);
		actionMap.put(RegionAfter.DISPLAY_ALIGN_ACTION,
				RegionAfterDisplayAlignAction.class);
		actionMap.put(RegionAfter.REFERENCE_ORIENTATION_ACTION,
				RegionAfterReferenceOrientationAction.class);
		actionMap.put(RegionAfter.OVERFLOW_ACTION,
				RegionAfterOverflowAction.class);
		actionMap.put(RegionAfter.BACKGROUND_COLOR_ACTION,
				RegionAfterBackgroundColorAction.class);
		actionMap.put(RegionAfter.BACKGROUND_IMAGE_ACTION,
				RegionAfterBackgroundImageAction.class);
		actionMap.put(RegionAfter.BACKGROUND_IMAGE_REMOVE_ACTION,
				RegionAfterBackgroundImageRemoveAction.class);
		/** 页脚动作 ************* 结束 *******/

		/** 左区域动作 ************* 开始 *******/
		actionMap.put(RegionStart.EXTENT_ACTION, RegionStartExtentAction.class);
		actionMap.put(RegionStart.DELETE_CONTENT_ACTION,
				RegionStartDeleteContentAction.class);

		actionMap.put(RegionStart.WRITING_MODE_ACTION,
				RegionStartWritingModeAction.class);
		actionMap.put(RegionStart.DISPLAY_ALIGN_ACTION,
				RegionStartDisplayAlignAction.class);
		actionMap.put(RegionStart.REFERENCE_ORIENTATION_ACTION,
				RegionStartReferenceOrientationAction.class);
		actionMap.put(RegionStart.OVERFLOW_ACTION,
				RegionStartOverflowAction.class);
		actionMap.put(RegionStart.BACKGROUND_COLOR_ACTION,
				RegionStartBackgroundColorAction.class);
		actionMap.put(RegionStart.BACKGROUND_IMAGE_ACTION,
				RegionStartBackgroundImageAction.class);
		actionMap.put(RegionStart.BACKGROUND_IMAGE_REMOVE_ACTION,
				RegionStartBackgroundImageRemoveAction.class);
		/** 左区域动作 ************* 结束 *******/

		/** 右区域动作 ************* 开始 *******/
		actionMap.put(RegionEnd.EXTENT_ACTION, RegionEndExtentAction.class);
		actionMap.put(RegionEnd.DELETE_CONTENT_ACTION,
				RegionEndDeleteContentAction.class);

		actionMap.put(RegionEnd.WRITING_MODE_ACTION,
				RegionEndWritingModeAction.class);
		actionMap.put(RegionEnd.DISPLAY_ALIGN_ACTION,
				RegionEndDisplayAlignAction.class);
		actionMap.put(RegionEnd.REFERENCE_ORIENTATION_ACTION,
				RegionEndReferenceOrientationAction.class);
		actionMap.put(RegionEnd.OVERFLOW_ACTION, RegionEndOverflowAction.class);
		actionMap.put(RegionEnd.BACKGROUND_COLOR_ACTION,
				RegionEndBackgroundColorAction.class);
		actionMap.put(RegionEnd.BACKGROUND_IMAGE_ACTION,
				RegionEndBackgroundImageAction.class);
		actionMap.put(RegionEnd.BACKGROUND_IMAGE_REMOVE_ACTION,
				RegionEndBackgroundImageRemoveAction.class);
		/** 右区域动作 ************* 结束 *******/

		/** PageSequence动作id所对应的action ** 开始 **/
		actionMap.put(PageSequence.SET_INITIAL_PAGE_NUMBER_ACTION,
				SetInitialPageNumberAction.class);
		actionMap.put(PageSequence.SET_FORCEPAGECOUNT_ACTION,
				SetForcePageCountAction.class);
		actionMap.put(PageSequence.SET_FORMAT_ACTION, SetFormatAction.class);
		actionMap.put(PageSequence.DELETE_ACTION, DeletePageSequenceAction.class);
		/** PageSequence动作id所对应的action ** 结束 **/

		/** 表格动作id所对应的action ** 开始 **/
		actionMap.put(Table.WIDTH_ACTION, TableWidthAction.class);
		actionMap.put(Table.WIDTH_INPUT_ACTION, TableWidthInputAction.class);

		actionMap.put(Table.END_POSITION_ACTION, TableEndIndentAction.class);
		actionMap
				.put(Table.START_POSITION_ACTION, TableStartIndentAction.class);
		actionMap.put(Table.BACKGROUND_COLOR_ACTION,
				TableBackgroundColorAction.class);
		actionMap.put(Table.BACKGROUND_IMAGE_ACTION,
				BackgroundImageSetAction.class);
		actionMap.put(Table.REMOVE_BACKGROUNDIMAGE,
				RemoveBackGroundImageAction.class);
		actionMap
				.put(Table.DISPLAY_ALIGN_ACTION, TableDisplayAlignAction.class);
		actionMap.put(Table.NONE_BORDER_ACTION, NoneBorderAction.class);
		actionMap.put(Table.AFTER_BORDER_ACTION, AfterBorderAction.class);
		actionMap.put(Table.BEFORE_BORDER_ACTION, BeforeBorderAction.class);
		actionMap.put(Table.START_BORDER_ACTION, StartBorderAction.class);
		actionMap.put(Table.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(Table.ALL_BORDER_ACTION, AllBorderAction.class);
		actionMap.put(TableCell.WIDTH_ACTION, TableCellWidthAction.class);
		actionMap.put(TableCell.BACKGROUND_COLOR_ACTION,
				TableCellBackgroundColorAction.class);
		actionMap.put(TableCell.BACKGROUND_IMAGE_ACTION,
				BackgroundImageSetAction.class);
		actionMap.put(TableCell.REMOVE_BACKGROUNDIMAGE,
				RemoveBackGroundImageAction.class);
		actionMap.put(TableCell.DISPLAY_ALIGN_ACTION,
				TableCellDisplayAlignAction.class);
		actionMap.put(TableRow.HEIGHT_ACTION, TableCellHeightAction.class);
		actionMap.put(TableCell.SIZE_TYPE, SizeTypeAction.class);
		actionMap.put(TableCell.NONE_BORDER_ACTION, NoneBorderAction.class);
		actionMap.put(TableCell.AFTER_BORDER_ACTION, AfterBorderAction.class);
		actionMap.put(TableCell.BEFORE_BORDER_ACTION, BeforeBorderAction.class);
		actionMap.put(TableCell.START_BORDER_ACTION, StartBorderAction.class);
		actionMap.put(TableCell.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(TableCell.ALL_BORDER_ACTION, AllBorderAction.class);
		actionMap.put(TableCell.LEFT_PADDING_ACTION,
				TableCellLeftPaddingAction.class);
		actionMap.put(TableCell.RIGHT_PADDING_ACTION,
				TableCellRightPaddingAction.class);
		actionMap.put(TableCell.TOP_PADDING_ACTION,
				TableCellTopPaddingAction.class);
		actionMap.put(TableCell.BOTTOM_PADDING_ACTION,
				TableCellBottomPaddingAction.class);
		/** 表格动作id所对应的action ** 结束 **/

		/** 条形码动作id所对应的action ** 开始 **/
		actionMap.put(Barcode.TYPE_39_ACTION, BarcodeType39Action.class);
		actionMap.put(Barcode.TYPE_39_DEFAULT_ACTION,
				BarcodeType39DefaultAction.class);
		actionMap.put(Barcode.TYPE_128_DEFAULT_ACTION,
				BarcodeType128DefaultAction.class);
		actionMap.put(Barcode.TYPE_UPCE_DEFAULT_ACTION,
				BarcodeTypeUPCEDefaultAction.class);
		actionMap.put(Barcode.TYPE_EAN8_DEFAULT_ACTION,
				BarcodeTypeEAN8DefaultAction.class);
		actionMap.put(Barcode.TYPE_UPCA_DEFAULT_ACTION,
				BarcodeTypeUPCADefaultAction.class);
		actionMap.put(Barcode.TYPE_EAN13_DEFAULT_ACTION,
				BarcodeTypeEAN13DefaultAction.class);
		actionMap.put(Barcode.TYPE_EAN128_DEFAULT_ACTION,
				BarcodeTypeEAN128DefaultAction.class);
		actionMap.put(Barcode.TYPE_25_DEFAULT_ACTION,
				BarcodeType25DefaultAction.class);
		actionMap.put(Barcode.INPUT_CONTENT_ACTION,
				BarcodeInputContentAction.class);
		actionMap.put(Barcode.ADD_CHECKSUM_ACTION,
				BarcodeAddCheckSumAction.class);
		actionMap.put(Barcode.MAKE_UCC_ACTION, BarcodeMakeUCCAction.class);
		actionMap.put(Barcode.QUIET_HORIZONTAL_ACTION,
				BarcodeQuietHorizontalAction.class);
		actionMap.put(Barcode.QUIET_VERTICAL_ACTION,
				BarcodeQuietVerticalAction.class);
		actionMap.put(Barcode.ORIENTATION_ACTION,
				BarcodeOrientationAction.class);
		actionMap.put(Barcode.HEIGHT_ACTION, BarcodeHeigthAction.class);
		actionMap.put(Barcode.WIDTH_ACTION, BarcodeWidthAction.class);
		actionMap
				.put(Barcode.FONT_FAMILY_ACTION, BarcodeFontFamilyAction.class);
		actionMap.put(Barcode.FONT_SIZE_ACTION, BarcodeFontSizeAction.class);
		actionMap.put(Barcode.SUBSET_ACTION, BarcodeSubSetAction.class);
		actionMap.put(Barcode.STRING_ACTION, BarcodeStringAction.class);
		actionMap.put(Barcode.PRINT_TEXT_ACTION, BarcodePrintTextAction.class);
		actionMap.put(Barcode.TEXT_CHAR_SPACE_ACTION,
				BarcodeTextCharSpaceAction.class);
		actionMap.put(Barcode.TEXT_BLOCK_ACTION, BarcodeTextBlockAction.class);
		actionMap
				.put(Barcode.CLEAN_STYLE_ACTION, BarcodeCleanStyleAction.class);
		actionMap.put(Barcode.WIDE_TO_NARROW_ACTION,
				BarcodeWideToNarrowAction.class);
		actionMap.put(Barcode.TYPE_PDF417_DEFAULT_ACTION,
				BarcodeTypePDF417DefaultAction.class);
		actionMap.put(Barcode.EC_LEVEL_ACTION, BarcodeECLevelAction.class);
		actionMap.put(Barcode.COLUMNS_ACTION, BarcodeColumnsAction.class);
		actionMap
				.put(Barcode.MIN_COLUMNS_ACTION, BarcodeMinColumnsAction.class);
		actionMap
				.put(Barcode.MAX_COLUMNS_ACTION, BarcodeMaxColumnsAction.class);
		actionMap.put(Barcode.MIN_ROWS_ACTION, BarcodeMinRowsAction.class);
		actionMap.put(Barcode.MAX_ROWS_ACTION, BarcodeMaxRowsAction.class);

		/** 条形码动作id所对应的action ** 结束 **/

		/** block-container动作id所对应的action ** 开始 **/
		actionMap.put(BlockContainer.TOP_POSITION_ACTION,
				BlockContainerTopPositionAction.class);
		actionMap.put(BlockContainer.LEFT_POSITION_ACTION,
				BlockContainerLeftPositionAction.class);
		actionMap.put(BlockContainer.END_POSITION_ACTION,
				BlockContainerEndPositionAction.class);
		actionMap.put(BlockContainer.START_POSITION_ACTION,
				BlockContainerStartPositionAction.class);
		actionMap.put(BlockContainer.BPD_ACTION, BlockContainerBPDAction.class);
		actionMap.put(BlockContainer.IPD_ACTION, BlockContainerIPDAction.class);

		actionMap.put(BlockContainer.AUTO_BPD_ACTION,
				BlockContainerBPDAutoAction.class);
		actionMap.put(BlockContainer.AUTO_IPD_ACTION,
				BlockContainerIPDAutoAction.class);

		actionMap.put(BlockContainer.OVERFLOW_VISIBLE_ACTION,
				BlockContainerOverFlowVisibleAction.class);
		actionMap.put(BlockContainer.OVERFLOW_HIDDEN_ACTION,
				BlockContainerOverFlowHiddenAction.class);
		actionMap.put(BlockContainer.OVERFLOW_AUTOFONTSIZE_ACTION,
				BlockContainerOverFlowAutoFontSizeAction.class);
		actionMap.put(BlockContainer.OVERFLOW_OTHER_ACTION,
				BlockContainerContentTreatAction.class);
		actionMap.put(BlockContainer.CLEAR_OVERFLOW_OTHER_ACTION,
				RemoveBlockContainerContentTreatAction.class);

		actionMap.put(BlockContainer.SIZEBYCONTENT_ACTION,
				SizeByContentAction.class);
		actionMap.put(BlockContainer.BACKGROUND_COLOR_ACTION,
				BackgroundColorAction.class);
		actionMap.put(BlockContainer.BACKGROUND_IMAGE_ACTION,
				BackgroundImageSetAction.class);
		actionMap.put(BlockContainer.REMOVE_BACKGROUNDIMAGE,
				RemoveBackGroundImageAction.class);
		actionMap.put(BlockContainer.DISPLAY_ALIGN_ACTION,
				BlockContainerDisplayAlignAction.class);
		actionMap
				.put(BlockContainer.NONE_BORDER_ACTION, NoneBorderAction.class);
		actionMap.put(BlockContainer.AFTER_BORDER_ACTION,
				AfterBorderAction.class);
		actionMap.put(BlockContainer.BEFORE_BORDER_ACTION,
				BeforeBorderAction.class);
		actionMap.put(BlockContainer.START_BORDER_ACTION,
				StartBorderAction.class);
		actionMap.put(BlockContainer.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(BlockContainer.ALL_BORDER_ACTION, AllBorderAction.class);

		actionMap.put(BlockContainer.PADDING_TOP,
				BlockContainerPaddingTopAction.class);
		actionMap.put(BlockContainer.PADDING_BOTTOM,
				BlockContainerPaddingBottomAction.class);
		actionMap.put(BlockContainer.PADDING_LEFT,
				BlockContainerPaddingLeftAction.class);
		actionMap.put(BlockContainer.PADDING_RIGHT,
				BlockContainerPaddingRightAction.class);
		/** block-container动作id所对应的action ** 结束 **/

		/** NumberText动作id所对应的action ** 开始 **/
		actionMap.put(NumberText.THOUSAND_SET_ACTION, WeishuSetAction.class);
		actionMap.put(NumberText.BAIFENBI_SET_ACTION,
				BaifenbiSetAction.class);
		actionMap.put(NumberText.THOUSAND_SEPARATOR_SET_ACTION,
				ThousandSeparatorSetAction.class);
		actionMap.put(NumberText.CHINESE_NUMBER_SET_ACTION,
				ChineseNumberSetAction.class);
		actionMap
				.put(NumberText.SET_ISCHINESE_ACTION, SetIsChineseAction.class);
		/** NumberText动作id所对应的action ** 结束 **/

		/** DateTime动作id所对应的action ** 开始 **/
		actionMap.put(DateTime.DATE_INPUT_SET_ACTION, DateInputSetAction.class);
		actionMap.put(DateTime.DATE_OUTPUT_SET_ACTION,
				DateOutputSetAction.class);

		actionMap.put(DateTime.INPUT_SEPEAATE_SET_ACTION,
				InputSeperatorAction.class);
		actionMap.put(DateTime.OUTPUT_SEPEAATE_SET_ACTION,
				OutputSeperatorAction.class);
		actionMap.put(DateTime.TIME_INPUT_SET_ACTION, TimeInputSetAction.class);
		actionMap.put(DateTime.TIME_OUTPUT_SET_ACTION,
				TimeOutputSetAction.class);
		/** DateTime动作id所对应的action ** 结束 **/

		/** ExternalGraph动作id所对应的action ** 开始 **/
		actionMap.put(ExternalGraphic.RESET_ACTION, ReSetAction.class);
		actionMap.put(ExternalGraphic.SET_ALPHA_ACTION, SetAlphaAction.class);
		actionMap.put(ExternalGraphic.SET_CONTENTHEIGHT_ACTION,
				SetContentHeightAction.class);
		actionMap.put(ExternalGraphic.SET_CONTENTWIDTH_ACTION,
				SetContentWidthAction.class);
		actionMap.put(ExternalGraphic.SET_DATATYPE_ACTION,
				SetDataTypeAction.class);
		actionMap.put(ExternalGraphic.SET_LAYER_ACTION, SetLayerAction.class);
		actionMap.put(ExternalGraphic.SET_SCALING_ACTION,
				SetScalingAction.class);
		actionMap.put(ExternalGraphic.SET_SIZETYPE_ACTION,
				SetSizeTypeAction.class);
		actionMap.put(ExternalGraphic.SET_SRCURL_ACTION, SetSrcURLAction.class);
		actionMap.put(ExternalGraphic.NONE_BORDER_ACTION,
				NoneBorderAction.class);
		actionMap.put(ExternalGraphic.AFTER_BORDER_ACTION,
				AfterBorderAction.class);
		actionMap.put(ExternalGraphic.BEFORE_BORDER_ACTION,
				BeforeBorderAction.class);
		actionMap.put(ExternalGraphic.START_BORDER_ACTION,
				StartBorderAction.class);
		actionMap.put(ExternalGraphic.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(ExternalGraphic.ALL_BORDER_ACTION, AllBorderAction.class);
		actionMap.put(ExternalGraphic.GRAPHIC_ISEDITABLE_ACTION, GraphicIsEditableAction.class);
		actionMap.put(ExternalGraphic.EDIT_AUTHORITY_ACTION, EditAuthorityAction.class);
		
		/** ExternalGraph动作id所对应的action ** 结束 **/

		/** SvgGraphic动作id所对应的action ** 结束 **/
		actionMap.put(SvgGraphic.CANVAS_HEIGHT,
				SvgGraphicCanvasHeightAction.class);
		actionMap.put(SvgGraphic.CANVAS_WIDTH,
				SvgGraphicCanvasWidthAction.class);
		actionMap.put(SvgGraphic.X_ACTION, SvgGraphicXAction.class);
		actionMap.put(SvgGraphic.Y_ACTION, SvgGraphicYAction.class);
		actionMap.put(SvgGraphic.HEIGHT_ACTION, SvgGraphicHeightAction.class);
		actionMap.put(SvgGraphic.WIDTH_ACTION, SvgGraphicWidthAction.class);
		actionMap.put(SvgGraphic.ROTATE_ACTION, SvgGraphicRotateAction.class);
		actionMap.put(SvgGraphic.LAYER_ACTION, SvgGraphicLayerAction.class);
		actionMap.put(SvgGraphic.OPACITY_ACTION, SvgGraphicOpacityAction.class);
		actionMap.put(SvgGraphic.FILL_COLOR_ACTION,
				SvgGraphicFillColorAction.class);
		actionMap.put(SvgGraphic.FILL_PICTURE_ACTION,
				SvgGraphicFillPictureAction.class);
		actionMap.put(SvgGraphic.REMOVE_PICTURE_ACTION,
				RemoveBackGroundImageAction.class);
		actionMap.put(SvgGraphic.FILL_OPACITY_ACTION,
				SvgGraphicFillOpacityAction.class);

		actionMap.put(SvgGraphic.FONT_FAMILY_ACTION,
				SvgGraphicFontFamilyAction.class);
		actionMap.put(SvgGraphic.FONT_SIZE_ACTION,
				SvgGraphicFontSizeAction.class);
		actionMap.put(SvgGraphic.FONT_CLEAN_ACTION,
				SvgGraphicFontCleanAction.class);
		actionMap.put(SvgGraphic.FONT_WEIGHT_ACTION,
				SvgGraphicFontWeightAction.class);
		actionMap.put(SvgGraphic.FONT_ITALIC_ACTION,
				SvgGraphicFontItalicAction.class);
		actionMap.put(SvgGraphic.FONT_UNDERLINE_ACTION,
				SvgGraphicFontUnderlineAction.class);
		actionMap.put(SvgGraphic.FONT_LINE_THROUGH_ACTION,
				SvgGraphicFontLineThroughAction.class);
		actionMap.put(SvgGraphic.FONT_COLOR_ACTION,
				SvgGraphicFontColorAction.class);
		actionMap.put(SvgGraphic.TEXT_CONTENT_ACTION,
				SvgGraphicTextContentAction.class);

		actionMap.put(SvgGraphic.BOTTOM_ALIGN, SvgBottomAlign.class);
		actionMap.put(SvgGraphic.TOP_ALIGN, SvgTopAlign.class);
		actionMap.put(SvgGraphic.VERTICAL_CENTER, SvgVerticalCenter.class);
		actionMap.put(SvgGraphic.LEFT_ALIGN, SvgLeftAlign.class);
		actionMap.put(SvgGraphic.RIGHT_ALIGN, SvgRightAlign.class);
		actionMap.put(SvgGraphic.HORIZONTAL_CENTER, SvgHorizontalCenter.class);

		actionMap.put(SvgGraphic.LINE_TYPE_ACTION,
				SvgGraphicLineTypeAction.class);
		actionMap.put(SvgGraphic.LINE_WIDTH_ACTION,
				SvgGraphicLineWidthAction.class);
		actionMap.put(SvgGraphic.LINE_COLOR_ACTION,
				SvgGraphicLineColorAction.class);

		actionMap.put(SvgGraphic.EQUAL_HEIGHT, SvgEqualHeight.class);
		actionMap.put(SvgGraphic.EQUAL_WIDTH, SvgEqualWidth.class);
		actionMap.put(SvgGraphic.STROKE_LINE_JOIN,
				SvgGraphicStrokeLineJoin.class);

		actionMap.put(SvgGraphic.ARROW_START_STYLE,
				SvgGraphicArrowStartStyle.class);
		actionMap
				.put(SvgGraphic.ARROW_END_STYLE, SvgGraphicArrowEndStyle.class);
		actionMap.put(SvgGraphic.RECTANGLE_RX, SvgGraphicRectangleRx.class);
		actionMap.put(SvgGraphic.RECTANGLE_RY, SvgGraphicRectangleRy.class);

		actionMap.put(SvgGraphic.ORIENTATION_ACTION,
				SvgGraphicOrientationAction.class);
		/** SvgGraphic动作id所对应的action ** 结束 **/

		/** WiseDocument动作id所对应的action ** 开始 **/
		actionMap.put(WiseDocument.EDITABLE, SetEditAction.class);

		actionMap.put(WiseDocument.DBTYPE, SetDBTypeAction.class);

		actionMap.put(WiseDocument.SET_WHITESPACECOLLAPSE_ACTION,
				SetWhiteSpaceCollapseAction.class);
		actionMap.put(WiseDocument.SET_WHITESPACETREATMENT_ACTION,
				SetWhiteSpaceTreatmentAction.class);
		actionMap.put(WiseDocument.SET_LINEFEEDTREATMENT_ACTION,
				SetLinefedTreatmentAction.class);
		/** WiseDocument动作id所对应的action ** 结束 **/

		/** Index动作id所对应的action ** 开始 **/
		actionMap.put(Paragraph.LEVEL_ACTION, IndexLevelAction.class);
		actionMap.put(Paragraph.LEVEL_CANCEL_ACTION,
				IndexLevelCancelAction.class);
		/** Index动作id所对应的action ** 结束 **/
		/** 【添加：START】注册统计图用Action by 李晓光 2009-5-21 */
		// ------------- 基本属性设置-------------
		actionMap.put(WisedocChart.BPD_ACTION, WisedocChartBPDAction.class);
		actionMap.put(WisedocChart.IPD_ACTION, WisedocChartIPDAction.class);
		actionMap.put(WisedocChart.MARGIN_ACTION,
				WisedocChartMarginAction.class);
		actionMap.put(WisedocChart.TITLE_ACTION, WisedocChartTitleAction.class);
		actionMap.put(WisedocChart.SERIES_COUNT_ACTION,
				WisedocChartSeriesCountAction.class);
		actionMap.put(WisedocChart.VALUE_COUNT_ACTION,
				WisedocChartValueCountAction.class);
		actionMap.put(WisedocChart.LAYER_ACTION, ChartLayerAction.class);
		actionMap.put(WisedocChart.BACK_ALPHA_ACTION,
				WisedocChartBackAlphaAction.class);
		actionMap.put(WisedocChart.FRONT_ALPHA_ACTION,
				WisedocChartFrontAlphaAction.class);
		actionMap.put(WisedocChart.BACKGROUND_COLOR_ACTION,
				WisedocChartBackGroundColorAction.class);
		actionMap.put(WisedocChart.FILL_PICTURE_ACTION,
				WisedocChartFillPictureAction.class);
		actionMap.put(WisedocChart.REMOVE_PICTURE_ACTION,
				RemoveBackGroundImageAction.class);
		actionMap.put(WisedocChart.SHOW_BASE_LINE_ACTION,
				WisedocChartShowBaseLineAction.class);
		actionMap.put(WisedocChart.SHOW_AXIS_Y_ACTION,
				WisedocChartShowAxisYAction.class);
		actionMap.put(WisedocChart.SHOW_AXIS_X_ACTION,
				WisedocChartShowAxisXAction.class);
		actionMap.put(WisedocChart.SHOW_3D_ACTION,
				WisedocChartShow3DAction.class);
		actionMap.put(WisedocChart.SHOW_VALUE_ACTION,
				WisedocChartShowValueAction.class);
		actionMap.put(WisedocChart.SHOW_ZERO_VALUE_ACTION,
				WisedocChartShowZeroValueAction.class);
		actionMap.put(WisedocChart.SHOW_NULL_VALUE_ACTION,
				WisedocChartShowNullValueAction.class);

		actionMap.put(WisedocChart.DEPTH3D_ACTION,
				WisedocChartDepth3DAction.class);
		actionMap.put(WisedocChart.VALUE_TEXT_ACTION,
				WisedocChartValueTextAction.class);
		actionMap.put(WisedocChart.SERIES_TEXT_ACTION,
				WisedocChartSeriesTextAction.class);

		actionMap.put(WisedocChart.MIN_ADUATE_ACTION,
				WisedocChartMinAduateAction.class);
		actionMap.put(WisedocChart.MAX_ADUATE_ACTION,
				WisedocChartMaxAduateAction.class);
		actionMap.put(WisedocChart.STEP_ADUATE_ACTION,
				WisedocChartStepAduateAction.class);
		actionMap.put(WisedocChart.AUTO_ADUATE_ACTION,
				WisedocChartAutoAction.class);
		actionMap.put(WisedocChart.ADUATE_PRECISION_ACTION,
				WisedocChartAduatePrecisionAction.class);
		actionMap.put(WisedocChart.OFFSET_ACTION,
				WisedocChartOffsetAction.class);
		actionMap.put(WisedocChart.CHART_ORIENTATION,
				WisedocCharttOrientation.class);
		actionMap.put(WisedocChart.INDICATOR_ACTION,
				WisedocChartIndicatorAction.class);
		actionMap.put(WisedocChart.START_DEGREE_ACTION,
				WisedocChartStartDegreeAction.class);
		actionMap.put(WisedocChart.PIE_ORIATION_ACTION,
				WisedocChartPieOriationAction.class);
		actionMap.put(WisedocChart.PIE_SHOW_LABEL_ACTION,
				WisedocChartPieShowLabelAction.class);
		actionMap.put(WisedocChart.PIE_SHOW_PERCENT_ACTION,
				WisedocChartPieShowPercentAction.class);
		actionMap.put(WisedocChart.PIE_SHOW_FACT_VALUE_ACTION,
				WisedocChartPieShowFactValueAction.class);

		actionMap.put(WisedocChart.FAMILY_ACTION, FontFamilyAction.class);

		actionMap
				.put(WisedocChart.AFTER_BORDER_ACTION, AfterBorderAction.class);
		actionMap.put(WisedocChart.BEFORE_BORDER_ACTION,
				BeforeBorderAction.class);
		actionMap
				.put(WisedocChart.START_BORDER_ACTION, StartBorderAction.class);
		actionMap.put(WisedocChart.END_BORDER_ACTION, EndBorderAction.class);
		actionMap.put(WisedocChart.NONE_BORDER_ACTION, NoneBorderAction.class);
		actionMap.put(WisedocChart.ALL_BORDER_ACTION, AllBorderAction.class);

		actionMap.put(WisedocChart.TYPE_ACTION, WisedocChartTypeAction.class);
		actionMap.put(WisedocChart.SERIES_LABEL_DEGREE_ACTION,
				WisedocChartSeriesLabelDegreeAction.class);

		// ------------- 坐标轴设置-------------
		// -------------选择框设置开始 -------------
		actionMap.put(CheckBox.CHECKBOX_UNSELECT_ACTION,
				CheckBoxUnselectValueAction.class);
		actionMap.put(CheckBox.CHECKBOX_SET_GROUPUI_ACTION,
				CheckBoxSetGroupUiAction.class);
		actionMap.put(CheckBox.CHECKBOX_CREAT_GROUPUI_ACTION,
				CheckBoxCreatGroupUiAction.class);
		actionMap.put(CheckBox.CHECKBOX_TICKMARK_ACTION,
				CheckBoxBoxTickMarkAction.class);
		actionMap.put(CheckBox.CHECKBOX_BOXSTYLE_ACTION,
				CheckBoxBoxStyleAction.class);
		actionMap.put(CheckBox.CHECKBOX_BOXSTYLE_LAYER_ACTION,
				SetBoxStyleLayerAction.class);
		actionMap.put(CheckBox.CHECKBOX_SELECT_ACTION,
				CheckBoxSelectValueAction.class);
		actionMap.put(CheckBox.EDIT_CONNWITH_ACTION, ConnwithAction.class);
		actionMap.put(CheckBox.REMOVE_CONNWITH_ACTION,
				RemoveConnwithAction.class);
		actionMap.put(CheckBox.CHECKBOX_ISSELECTED_ACTION,
				CheckBoxIsSelectedAction.class);
		actionMap.put(CheckBox.CHECKBOX_ISEDITABLE_ACTION,
				IsEditableAction.class);
		actionMap
				.put(CheckBox.EDIT_AUTHORITY_ACTION, EditAuthorityAction.class);
		actionMap.put(CheckBox.EDIT_ISRELOAD_ACTION, EditIsReloadAction.class);
		// -------------选择框设置结束 -------------

		/** 【添加：END】 by 李晓光 2009-5-21 */
		// 编辑设置相关的Action 开始 add by zq
		actionMap.put(Edit.EDIT_TYPE_ACTION, EditTypeAction.class);
		actionMap.put(Edit.EDIT_AUTHORITY_ACTION, EditAuthorityAction.class);
		actionMap.put(Edit.EDIT_ISRELOAD_ACTION, EditIsReloadAction.class);
		actionMap.put(Edit.EDIT_APPEARANCE_ACTION, EditAppearanceAction.class);
		actionMap.put(Edit.EDIT_WIDTH_ACTION, EditWidthAction.class);
		actionMap.put(Edit.EDIT_HEIGHT_ACTION, EditHeightAction.class);
		actionMap.put(Edit.EDIT_HINT_ACTION, EditHintAction.class);
		actionMap.put(Edit.EDIT_INPUTTYPE_ACTION, InputTypeAction.class);
		actionMap.put(Edit.EDIT_INPUTMULTILINE_ACTION,
				InputMultilineAction.class);
		actionMap.put(Edit.EDIT_INPUTWRAP_ACTION, InputWrapAction.class);
		actionMap.put(Edit.EDIT_INPUTFILTER_ACTION, InputFilterAction.class);
		actionMap.put(Edit.EDIT_INPUTFILTERMSG_ACTION,
				InputFilterMsgAction.class);
		actionMap.put(Edit.EDIT_INPUTDATASOURCE_ACTION,
				InputDataSourceAction.class);
		actionMap.put(Edit.EDIT_DEFAULT_VALUE_ACTION,
				DefaultValueAction.class);
		actionMap.put(Edit.EDIT_DATETYPE_ACTION, DateTypeAction.class);
		actionMap.put(Edit.EDIT_ONBLURVALIDATION_ACTION,
				OnBlurValidationAction.class);
		actionMap.put(Edit.EDIT_ONEDITVALIDATION_ACTION,
				OnEditValidationAction.class);
		actionMap.put(Edit.EDIT_ONRESULTVALIDATION_ACTION,
				OnResultValidationAction.class);
		actionMap.put(Edit.REMOVE_ONBLURVALIDATION_ACTION,
				RemoveOnblurValidationAction.class);
		actionMap.put(Edit.REMOVE_ONEDITVALIDATION_ACTION,
				RemoveOnEditValidationAction.class);
		actionMap.put(Edit.REMOVE_ONRESULTVALIDATION_ACTION,
				RemoveOnResultValidationAction.class);

		actionMap.put(Edit.EDIT_SELECTTYPE_ACTION, SelectTypeAction.class);
		actionMap.put(Edit.EDIT_SELECTLINES_ACTION, SelectLinesAction.class);
		actionMap.put(Edit.EDIT_SELECTEDITABLE_ACTION,
				SelectEditableAction.class);
		actionMap.put(Edit.EDIT_SELECTSHOWLIST_ACTION,
				SelectShowListAction.class);
		actionMap.put(Edit.EDIT_SELECTMULTIPLE_ACTION,
				SelectMultipleAction.class);
		actionMap.put(Edit.EDIT_SELECTINFO_ACTION, SelectInfoAction.class);
		actionMap.put(Edit.EDIT_POPUPBROWSERINFO_ACTION, PopupBrowserInfoAction.class);
		actionMap.put(Edit.EDIT_SELECTNEXT_ACTION, SetSelectNextAction.class);
		actionMap.put(Edit.REMOVE_SELECTNEXT_ACTION,
				RemoveSelectNextAction.class);
		actionMap.put(Edit.EDIT_SELECTNEXTNAME_ACTION,
				SetSelectNameAction.class);
		actionMap.put(Edit.EDIT_CONNWITH_ACTION, ConnwithAction.class);
		actionMap.put(Edit.REMOVE_CONNWITH_ACTION, RemoveConnwithAction.class);
		// 编辑设置相关的Action 结束

		// add by zyj
		actionMap.put(Datatreat.SET_DATATREAT_TRANSFORM_TABLE,
				SetDatatreatTransformTableAction.class);
		actionMap.put(Datatreat.SET_DATATREAT_XPATH_POSITION,
				SetDatatreatXpathPositionAction.class);
		actionMap.put(Datatreat.REMOVE_DATATREAT_TRANSFORM_TABLE,
				RemoveDatatreatTransformTableAction.class);
		actionMap.put(Datatreat.REMOVE_DATATREAT_XPATH_POSITION,
				RemoveDatatreatXpathPositionAction.class);
		actionMap.put(Datatreat.SET_SIMPLE_DATA_TRANSFORM_TABLE,
				SetSimpleDatatreatTransformTableAction.class);
		actionMap.put(Datatreat.REMOVE_SIMPLE_DATA_TRANSFORM_TABLE,
				RemoveSimpleDatatreatTransformTableAction.class);

		actionMap.put(Datatreat.SET_BUTTONS, SetButtonsAction.class);

		actionMap.put(Datatreat.REMOVE_BUTTONS, RemoveButtonsAction.class);
		// end add
		// 艺术字相关的Action开始
		actionMap.put(WordArtText.SET_TEXTCONTENT_ACTION,
				SetTextContentAction.class);
		actionMap.put(WordArtText.SET_LETTERSPACE_ACTION,
				SetLetterSpaceAction.class);
		actionMap.put(WordArtText.SET_ALIGNMENT_ACTION,
				SetAlignmentAction.class);
		actionMap.put(WordArtText.SET_PATHTYPE_ACTION, SetPathTypeAction.class);
		actionMap.put(WordArtText.SET_ROTATION_ACTION, SetRotationAction.class);
		actionMap.put(WordArtText.SET_STARTPOSITION_ACTION,
				SetStartPositionAction.class);
		actionMap.put(WordArtText.SET_PATHVISABLE_ACTION,
				SetPathVisableAction.class);
		actionMap.put(WordArtText.SET_WIDTH_ACTION, setWidthAction.class);
		actionMap.put(WordArtText.SET_HEIGHT_ACTION, SetHeightAction.class);
		// 艺术字相关的Action结束

		// 加密文本相关开始
		actionMap.put(EncryptText.SET_ENCRYPT_ACTION, SetEncryptAction.class);
		// 加密文本结束
		actionMap.put(QianZhang.INSERT_ACTION, InsertQianzhangAction.class);
		actionMap.put(QianZhang.CHANGE_ACTION, SetSrcAction.class);
	}

	/**
	 * 该静态方法可以直接创建相应的动作，如果该动作不存在则创建动作，如果该动作存在则返回该动作的引用
	 * 
	 * @param actionID
	 *            枚举类型中的Action_id，目前枚举类型有Font, Paragraph, Page, Table,
	 *            Graph和BlockContiner
	 * @return 实体动作类的引用
	 */
	public static BaseAction getAction(final Enum<? extends ActionID> actionID)
	{

		if (activeActions.get(actionID) != null)
		{
			return activeActions.get(actionID);
		} else
		{

			final Class<? extends BaseAction> c = actionMap.get(actionID);
			if (c == null)
			{
				return new DefaultAction(ActionType.UNDEFINED);
			}
			BaseAction action = null;

			Constructor<? extends BaseAction> constructor = null;

			try
			{
				constructor = c.getConstructor();
				// constructor = c.getConstructor(ActionType.class);
			} catch (final SecurityException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (final NoSuchMethodException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try
			{
				action = constructor.newInstance();
				// action = constructor.newInstance(getPropertyType(actionID));
			} catch (final IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final InstantiationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				// System.out.println(e.getCause());
				e.printStackTrace();
			}

			if (action instanceof Actions)
			{
				// 设置Action类型
				((Actions) action).setActionType(((ActionID) actionID)
						.getType());
				// 设置actionID
				((Actions) action).setActionID(actionID);
			}

			activeActions.put(actionID, action);

			return action;
		}
	}

	/**
	 * 该静态方法可以直接创建相应的动作
	 * 
	 * @param actionConstants
	 *            ActionConstants中的Action_id
	 * @param components
	 *            如果该动作是由一组按钮组成的，则把所有相关的按钮都传递进来，该参数也可以没有
	 * @return
	 */
	public static BaseAction getAction(final Enum<? extends ActionID> actionID,
			final Object... relatedComponents)
	{

		// 设置和该动作相关的组件
		// action.setRelatedComponents(relatedComponents);

		return getAction(actionID);
	}

	/**
	 * 取得目前被激活的actions
	 * 
	 * @return 被激活的actions
	 */
	public static Map<Enum<? extends ActionID>, BaseAction> getActiveActions()
	{
		return activeActions;
	}

	// XXX 测试统计用，需要被删除
	public static Map<Enum<? extends ActionID>, Class<? extends BaseAction>> getActionMap()
	{
		return actionMap;
	}

}
