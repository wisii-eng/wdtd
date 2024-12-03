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
package com.wisii.wisedoc.layoutmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.area.AreaTreeHandler;
import com.wisii.wisedoc.area.Footnote;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.EncryptTextInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineLevel;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageNumberCitationLast;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.PositionInline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.QianZhangInline;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.Title;
import com.wisii.wisedoc.document.TotalPageNumber;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.SideRegion;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.document.svg.WordArtText;
import com.wisii.wisedoc.layoutmanager.inline.BarCodeLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.BasicLinkLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.ChartLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.ContentLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.ExternalGraphicLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.InlineLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.InstreamForeignObjectLM;
import com.wisii.wisedoc.layoutmanager.inline.LeaderLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.PageNumberCitationLastLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.PageNumberCitationLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.PageNumberLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.QianZhangLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.TextLayoutManager;
import com.wisii.wisedoc.layoutmanager.table.TableLayoutManager;
import com.wisii.wisedoc.log.LogUtil;

/** The default LayoutManager maker class */
public class LayoutManagerMapping implements LayoutManagerMaker
{
    /** The map of LayoutManagerMakers */
    private Map makers = new HashMap();

    public LayoutManagerMapping()
    {
        initialize();
    }

    /** Initializes the set of maker objects associated with this LayoutManagerMapping     */
    protected void initialize()
    {
        makers.put(FOText.class, new FOTextLayoutManagerMaker());
        /*makers.put(FObjMixed.class, new Maker());
        makers.put(BidiOverride.class, new BidiOverrideLayoutManagerMaker());*/
        makers.put(Inline.class, new InlineLayoutManagerMaker());
        makers.put(TextInline.class, new InlineLayoutManagerMaker());
        makers.put(ImageInline.class, new InlineLayoutManagerMaker());
        makers.put(DateTimeInline.class, new InlineLayoutManagerMaker());
        makers.put(NumberTextInline.class, new InlineLayoutManagerMaker());
        makers.put(PositionInline.class, new InlineLayoutManagerMaker());
        makers.put(BarCodeInline.class, new InlineLayoutManagerMaker());
        makers.put(ChartInline.class, new InlineLayoutManagerMaker());
        makers.put(CheckBoxInline.class, new InlineLayoutManagerMaker());
        makers.put(Footnote.class, new FootnodeLayoutManagerMaker());
 /*       makers.put(InlineContainer.class, new InlineContainerLayoutManagerMaker());*/
        makers.put(BasicLink.class, new BasicLinkLayoutManagerMaker());
        makers.put(Block.class, new BlockLayoutManagerMaker());
        makers.put(Leader.class, new LeaderLayoutManagerMaker());
        /*makers.put(RetrieveMarker.class, new RetrieveMarkerLayoutManagerMaker());*/
        makers.put(Character.class, new CharacterLayoutManagerMaker());
        makers.put(ExternalGraphic.class,  new ExternalGraphicLayoutManagerMaker());
        makers.put(BarCode.class,  new BarCodeLayoutManagerMaker());
        makers.put(Chart.class,  new ChartLayoutManagerMaker());
        makers.put(BlockContainer.class,  new BlockContainerLayoutManagerMaker());
        makers.put(TableContents.class,  new BlockContainerLayoutManagerMaker());
        makers.put(PageNumber.class, new PageNumberLayoutManagerMaker());
        makers.put(PageNumberCitation.class,  new PageNumberCitationLayoutManagerMaker());
        makers.put(PageNumberCitationLast.class, new PageNumberCitationLastLayoutManagerMaker());
        makers.put(TotalPageNumber.class, new PageNumberCitationLastLayoutManagerMaker());
        makers.put(Table.class, new TableLayoutManagerMaker());
        makers.put(TableCell.class, new Maker());
        makers.put(Title.class, new InlineLayoutManagerMaker());
//        add by zq 增加了svg图形的排版功能
        makers.put(SVGContainer.class,  new SVGContainerLayoutManagerMaker());
        makers.put(Canvas.class,  new InstreamForeignObjectLayoutManagerMaker());
        makers.put(WordArtText.class,  new InstreamForeignObjectLayoutManagerMaker());
//       zq add end
        makers.put(Group.class, new GroupLayoutManagerMaker());
        makers.put(EncryptTextInline.class, new InlineLayoutManagerMaker());
        makers.put(ZiMoban.class, new ZiMobanLayoutManagerMaker());
        
        makers.put(QianZhangInline.class, new InlineLayoutManagerMaker());
        makers.put(QianZhang.class, new QianZhangLayoutManagerMaker());
    }

    /** @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeLayoutManagers(Element, List)    */
    public void makeLayoutManagers(Element node, List lms)
    {
        Maker maker = (Maker) makers.get(node.getClass());
        if (maker == null)
            LogUtil.error("No LayoutManager maker for class " + node.getClass());
        else
            maker.make(node, lms);
    }

    /** @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeLayoutManager(Element)     */
    public LayoutManager makeLayoutManager(Element node)
    {
        List lms = new ArrayList();
        makeLayoutManagers(node, lms);
        if (lms.size() == 0)
            throw new IllegalStateException("类 " + node.getClass() + " 的布局管理丢失.");
       else if (lms.size() > 1)
            throw new IllegalStateException("重复定义LayoutMangers");
        return (LayoutManager) lms.get(0);
    }

    public PageSequenceLayoutManager makePageSequenceLayoutManager(AreaTreeHandler ath, PageSequence ps)
    {
        return new PageSequenceLayoutManager(ath, ps);
    }

    /* @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeFlowLayoutManager(PageSequenceLayoutManager, Flow)   */
    public FlowLayoutManager makeFlowLayoutManager(PageSequenceLayoutManager pslm, Flow flow)
    {
        return new FlowLayoutManager(pslm, flow);
    }

    /* @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeContentLayoutManager(PageSequenceLayoutManager, Title)     */
    public ContentLayoutManager makeContentLayoutManager(PageSequenceLayoutManager pslm, Title title)
    {
        return new ContentLayoutManager(pslm, title);
    }

    /* @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeStaticContentLayoutManager(PageSequenceLayoutManager, StaticContent, Region)     */
    public StaticContentLayoutManager makeStaticContentLayoutManager(PageSequenceLayoutManager pslm, StaticContent sc, SideRegion reg)
    {
        return new StaticContentLayoutManager(pslm, sc, reg);
    }

    /* @see com.wisii.wisedoc.layoutmanager.LayoutManagerMaker#makeStaticContentLayoutManager(PageSequenceLayoutManager, StaticContent, Block)     */
    public StaticContentLayoutManager makeStaticContentLayoutManager(PageSequenceLayoutManager pslm, StaticContent sc, com.wisii.wisedoc.area.Block block)
    {
        return new StaticContentLayoutManager(pslm, sc, block);
    }

    public static class Maker
    {
        public void make(Element node, List lms)
        {
            // no layout manager
            return;
        }
    }

    public static class FOTextLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            FOText foText = (FOText) node;
            int length = foText.endIndex - foText.startIndex;
            if ( length> 0 || (length == 0 && Inline.class.equals(node.getParent().getClass())))
            {
                lms.add(new TextLayoutManager(foText));
            }
        }
    }

/*
    public static class FObjMixedLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            if (node.getChildNodes() != null)
            {
                InlineStackingLayoutManager lm;
                lm = new InlineStackingLayoutManager((FObjMixed) node);
                lms.add(lm);
            }
        }
    }
*/

    public static class BidiOverrideLayoutManagerMaker extends Maker
    {
        // public static class BidiOverrideLayoutManagerMaker extends FObjMixedLayoutManagerMaker {
        public void make(Element node, List lms)
        {
            /*if (false)
            {
                // this is broken; it does nothing it should make something like an InlineStackingLM
                super.make(node, lms);
            }
            else
            {
                ArrayList childList = new ArrayList();
                // this is broken; it does nothing it should make something like an InlineStackingLM
                super.make(node, childList);
                for (int count = childList.size() - 1; count >= 0; count--)
                {
                    LayoutManager lm = (LayoutManager) childList.get(count);
                    if (lm instanceof InlineLevelLayoutManager)
                    {
                        LayoutManager blm = new BidiLayoutManager(node, (InlineLayoutManager) lm);
                        lms.add(blm);
                    }
                    else
                        lms.add(lm);
                }
            }*/
        }
    }

    public static class InlineLayoutManagerMaker extends Maker
    {
         public void make(Element node, List lms)
         {
             if (node instanceof Inline&&((CellElement)node).getChildNodes() != null)
                 lms.add(new InlineLayoutManager((InlineLevel) node));
         }
    }

    public static class FootnodeLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            /*lms.add(new FootnoteLayoutManager((Footnote) node));*/
        }
    }
    public static class BasicLinkLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            lms.add(new BasicLinkLayoutManager((BasicLink) node));
        }
    }

    public static class BlockLayoutManagerMaker extends Maker
    {
         public void make(Element node, List lms)
         {
             lms.add(new BlockLayoutManager((Block) node));
         }
    }

    public static class LeaderLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            lms.add(new LeaderLayoutManager((Leader) node));
        }
    }

    public static class CharacterLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            /*Character foCharacter = (Character) node;
            if (foCharacter.getCharacter() != CharUtilities.CODE_EOT)
            {
                lms.add(new CharacterLayoutManager(foCharacter));
            }*/
        }
    }

    public static class ExternalGraphicLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            ExternalGraphic eg = (ExternalGraphic) node;
            if (!eg.getSrc().equals(""))
            {
                lms.add(new ExternalGraphicLayoutManager(eg));
            }
        }
    }
    public static class BarCodeLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
                lms.add(new BarCodeLayoutManager((BarCode) node));
        }
    }
    public static class ChartLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
                lms.add(new ChartLayoutManager((Chart) node));
        }
    }
    
    public static class QianZhangLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
                lms.add(new QianZhangLayoutManager((QianZhang) node));
        }
    }
    public static class BlockContainerLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            lms.add(new BlockContainerLayoutManager((BlockContainer) node));
         }
    }

    public static class ListItemLayoutManagerMaker extends Maker
    {
         public void make(Element node, List lms)
         {
             /*lms.add(new ListItemLayoutManager((ListItem) node));*/
         }
    }

    public static class ListBlockLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            /*lms.add(new ListBlockLayoutManager((ListBlock) node));*/
        }
    }

    public static class InstreamForeignObjectLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            lms.add(new InstreamForeignObjectLM((Canvas) node));
        }
    }

    public static class PageNumberLayoutManagerMaker extends Maker
    {
         public void make(Element node, List lms)
         {
             lms.add(new PageNumberLayoutManager((PageNumber) node));
         }
    }

    public static class PageNumberCitationLayoutManagerMaker extends Maker
    {
         public void make(Element node, List lms)
         {
            lms.add(new PageNumberCitationLayoutManager((PageNumberCitation) node));
         }
    }

    public static class PageNumberCitationLastLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
           lms.add(new PageNumberCitationLastLayoutManager((PageNumberCitationLast) node));
        }
    }

    public static class TableLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            Table table = (Table) node;
            TableLayoutManager tlm = new TableLayoutManager(table);
            lms.add(tlm);
        }
    }

    public class RetrieveMarkerLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
           /* Iterator baseIter;
            baseIter = node.getChildNodes();
            if (baseIter == null)
                return;
            while (baseIter.hasNext())
            {
                Element child = (Element) baseIter.next();
                makeLayoutManagers(child, lms);
            }*/
        }
    }
    public static class SVGContainerLayoutManagerMaker extends Maker
    {
        public void make(Element node, List lms)
        {
            lms.add(new SVGContainerLayoutManager((SVGContainer) node));
         }
    }
    public class GroupLayoutManagerMaker extends Maker
    {
    	@Override
        public void make(Element node, List lms)
        {
    		Group group = (Group)node;
            /*lms.add(new GroupLayoutManager(group,true));*/
        	ListIterator baseIter = group.getChildNodes();
            if (baseIter == null)
                return;
            while (baseIter.hasNext())
            {
                Element child = (Element) baseIter.next();
                makeLayoutManagers(child, lms);
            }
           /* lms.add(new GroupLayoutManager(group,false));*/
        }
    }
    public class ZiMobanLayoutManagerMaker extends Maker
    {
    	@Override
        public void make(Element node, List lms)
        {
    		ZiMoban zimoban = (ZiMoban)node;
            /*lms.add(new GroupLayoutManager(group,true));*/
        	ListIterator baseIter = zimoban.getChildNodes();
            if (baseIter == null)
                return;
            while (baseIter.hasNext())
            {
                Element child = (Element) baseIter.next();
                makeLayoutManagers(child, lms);
            }
           /* lms.add(new GroupLayoutManager(group,false));*/
        }
    }
}
