package mod.instance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Point;

import javax.swing.JPanel;

import Define.AreaDefine;
import Pack.DragPack;
import bgWork.handler.CanvasPanelHandler;
import mod.IFuncComponent;
import mod.ILinePainter;
import java.lang.Math;

public abstract class ConnectionLine extends JPanel
		implements IFuncComponent, ILinePainter
{
	JPanel				from;
	int					fromSide;
	Point				fp				= new Point(0, 0);
	JPanel				to;
	int					toSide;
	Point				tp				= new Point(0, 0);
	boolean				isSelect		= false;
	int					selectBoxSize	= 50;
	CanvasPanelHandler	cph;

	public ConnectionLine(CanvasPanelHandler cph)
	{
		this.setOpaque(false);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1, 1));
		this.cph = cph;
	}

    public JPanel getFromPortPanel() {
        JPanel portPanel = new JPanel();
        portPanel.setSize(20, 20);
        portPanel.setLocation(fp.x - 10, fp.y - 10);
        return portPanel;
    }

    public JPanel getToPortPanel() {
        JPanel portPanel = new JPanel();
        portPanel.setSize(20, 20);
        portPanel.setLocation(tp.x - 10, tp.y - 10);
        return portPanel;
    }

	// @Override
	// public void paintComponent(Graphics g)
	// {
	// 	Point fpPrime;
	// 	Point tpPrime;
	// 	renewConnect();
	// 	fpPrime = new Point(fp.x - this.getLocation().x,
	// 			fp.y - this.getLocation().y);
	// 	tpPrime = new Point(tp.x - this.getLocation().x,
	// 			tp.y - this.getLocation().y);

	// 	if (isSelect == true)
	// 	{
	// 		paintSelect(g);
	// 	}

	// 	g.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
	// 	paintArrow(g, tpPrime);
	// }

	@Override
	public void reSize()
	{
		Dimension size = new Dimension(Math.abs(fp.x - tp.x) + 10,
				Math.abs(fp.y - tp.y) + 10);
		this.setSize(size);
		this.setLocation(Math.min(fp.x, tp.x) - 5, Math.min(fp.y, tp.y) - 5);
	}

	// @Override
	// public void paintArrow(Graphics g, Point point)
	// {
	// 	// TODO Auto-generated method stub
	// }

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

	void renewConnect()
	{
		try
		{
			fp = getConnectPoint(from, fromSide);
			tp = getConnectPoint(to, toSide);
			this.reSize();
		}
		catch (NullPointerException e)
		{
			this.setVisible(false);
			cph.removeComponent(this);
		}
	}

	Point getConnectPoint(JPanel jp, int side)
	{
		Point temp = new Point(0, 0);
		Point jpLocation = cph.getAbsLocation(jp);
		if (side == new AreaDefine().TOP)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
			temp.y = jpLocation.y;
		}
		else if (side == new AreaDefine().RIGHT)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth());
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
		}
		else if (side == new AreaDefine().LEFT)
		{
			temp.x = jpLocation.x;
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
		}
		else if (side == new AreaDefine().BOTTOM)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight());
		}
		else
		{
			temp = null;
			System.err.println("getConnectPoint fail:" + side);
		}
		return temp;
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

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
        System.out.println("line setSelect " + isSelect);
		this.isSelect = isSelect;
	}
}

