package mod.instance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Point;
import java.awt.BasicStroke;

import javax.swing.JPanel;

import Define.AreaDefine;
import Pack.DragPack;
import bgWork.handler.CanvasPanelHandler;
import mod.IFuncComponent;
import mod.ILinePainter;
import java.lang.Math;

public class DependencyLine extends ConnectionLine {

	public DependencyLine(CanvasPanelHandler cph)
	{
        super(cph);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Point fpPrime;
		Point tpPrime;
		renewConnect();
		fpPrime = new Point(fp.x - this.getLocation().x,
				fp.y - this.getLocation().y);
		tpPrime = new Point(tp.x - this.getLocation().x,
				tp.y - this.getLocation().y);

		if (isSelect == true)
		{
			paintSelect(g);
		}

        Graphics2D g2d = (Graphics2D) g.create();

        // Set the stroke of the copy, not the original 
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                        0, new float[]{9}, 0);
        g2d.setStroke(dashed);
		g2d.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);

		paintArrow(g, tpPrime);
	}

	@Override
	public void reSize()
	{
		Dimension size = new Dimension(Math.abs(fp.x - tp.x) + 10,
				Math.abs(fp.y - tp.y) + 10);
		this.setSize(size);
		this.setLocation(Math.min(fp.x, tp.x) - 5, Math.min(fp.y, tp.y) - 5);
	}

	@Override
	public void paintArrow(Graphics g, Point point)
	{
        g.drawLine(point.x, point.y, point.x-10, point.y-10);
        g.drawLine(point.x, point.y, point.x+10, point.y-10);
	}

	@Override
	public void setConnect(DragPack dPack)
	{
		Point mfp = dPack.getFrom();
		Point mtp = dPack.getTo();
		from = (JPanel) dPack.getFromObj();
		to = (JPanel) dPack.getToObj();
		fromSide = new AreaDefine().getArea(from.getLocation(), from.getSize(),
				mfp);
		toSide = new AreaDefine().getArea(to.getLocation(), to.getSize(), mtp);
		renewConnect();
		System.out.println("from side " + fromSide);
		System.out.println("to side " + toSide);
	}

	@Override
	public void paintSelect(Graphics g)
	{
		Point fpPrime;
		Point tpPrime;
		fpPrime = new Point(fp.x - this.getLocation().x,
				fp.y - this.getLocation().y);
		tpPrime = new Point(tp.x - this.getLocation().x,
				tp.y - this.getLocation().y);

        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(5);
        g2d.setStroke(dashed);
        g2d.setColor(Color.CYAN);
        g2d.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
	}
}
